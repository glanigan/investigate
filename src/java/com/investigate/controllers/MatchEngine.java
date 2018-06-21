package com.investigate.controllers;

import com.investigate.models.Condition;
import com.investigate.models.Condition.ConditionType;
import com.investigate.models.Configuration;
import com.investigate.models.Match;
import com.investigate.models.Rule;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MatchEngine {

    private List<Match> matches;
    private DBManager db;

    protected void setupDatebase() {
        this.db = new DBManager();
        this.db.connect();
    }

    public void runMatching(int applicationNo) {
        System.out.println("Matching started");
        try {
            setupDatebase();
            matchApplication(applicationNo);
            db.insertMatches(matches);
            System.out.println("Matches inserted");
            calculateRecommendation(applicationNo, calculateRiskScore(matches, applicationNo));
        } catch (SQLException ex) {
            System.err.println("Error with matching");
        } finally {
            this.db.close();
            System.out.println("Matching complete");
        }
    }

    protected void matchApplication(int appNo) {
        try {
            if (db.getApplication(appNo).getReference() == null) {
                throw new Exception("Application not found");
            }
            List<Rule> rules = this.db.getEnabledRules();
            this.matches = new ArrayList();
            String ruleQuery;
            
            for (Rule rule : rules) {
                List<Condition> conditions = rule.getConditions();
                if (conditions.size() > 0) {
                    //Base SQL
                    ruleQuery = " FROM APPLICATION APPLHS"
                            + " INNER JOIN APPLICATION APPRHS "
                            + " ON APPLHS.REFERENCE<>APPRHS.REFERENCE ";
                    //Core Conditions
                    if (conditions.stream()
                            .anyMatch((c) -> (c.getType().equals(ConditionType.CORE) && c.getQuery() != null))) {
                        ruleQuery = conditions.stream()
                                .filter((c) -> (c.getType().equals(ConditionType.CORE) && c.getQuery() != null))
                                .map((c) -> " " + c.getQuery())
                                .reduce(ruleQuery, String::concat);
                    }
                    ruleQuery = ruleQuery + " WHERE APPLHS.APP_NO=" + appNo + " ";
                    //Filter conditions
                    ruleQuery = conditions.stream()
                            .filter((c) -> (c.getType().equals(ConditionType.FILTER)))
                            .map((c) -> " " + c.getQuery().replace("@PARAM", c.getParameter())+" ")
                            .reduce(ruleQuery, String::concat);
                    //Group conditions
                    for (Condition c : conditions) {
                        if (c.getType().equals(ConditionType.GROUP)) {
                            String[] querySplit = c.getQuery().replace("@PARAM", c.getParameter()).split("@SPLIT");
                            ruleQuery += querySplit[0] + ruleQuery + querySplit[1];
                        }
                    }
                    ruleQuery = "SELECT APPRHS.APP_NO AS RHS" + ruleQuery;
                    System.out.println(ruleQuery);
                    ResultSet rs;
                    try {
                        rs = db.performMatching(ruleQuery);
                        while (rs.next()) {
                            Match match = new Match(
                                    appNo,
                                    rs.getInt("RHS"),
                                    rule
                            );
                            this.matches.add(match);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(MatchEngine.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.err.println("Error: " + rule.getName() + ": No conditions found");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MatchEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected int calculateRiskScore(List<Match> matches, int applicationNo) throws SQLException {
        int riskScore = 0;
        riskScore = matches.stream()
                .map((match) -> match.getRule())
                .map((rule) -> rule.getScore())
                .reduce(riskScore, Integer::sum);
        db.setRiskScore(riskScore, applicationNo);
        System.out.println(riskScore);
        return riskScore;
    }

    protected void calculateRecommendation(int applicationNo, int riskScore) throws SQLException {
        String recommendation = "APPROVED";
        Configuration configuration = db.getConfiguration();
        
        System.out.println("Decision Making Stage");
        if (configuration.isrDecision()) {
            RManager rc = new RManager();
            if (rc.connect()) {
                recommendation = rc.getRecommendation(applicationNo);
            } else {
                System.err.println("Unable to connect to R server");
            }
        }
        if (configuration.isScoreDecision()) {
            if (riskScore >= configuration.getScoreDecisionInt()) {
                recommendation = "REFER";
            }
        }
        if (configuration.isMatchDecision()) {
            if (!matches.isEmpty()) {
                recommendation = "REFER";
            }
        }
        db.setRecommendation(recommendation, applicationNo);
    }
}
