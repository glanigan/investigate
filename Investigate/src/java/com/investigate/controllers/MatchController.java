/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.controllers;

import com.investigate.entities.Match;
import com.investigate.entities.Rule;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author garyl
 */
public class MatchController {
    
    private List<Rule> rules;
    private List<Match> matches;
    private DBManager db;
    
    public MatchController(){}
    public void setupDatebase(){
        this.db = new DBManager();
        this.db.connect();
    }
    public void runMatching(int applicationNo){
        System.out.println("Matching started");
        try {
            setupDatebase();
            matchApplication(applicationNo);
            db.insertMatches(matches);
            int riskScore;
            riskScore=calculateRiskScore(matches,applicationNo);
            calculateRecommendation(applicationNo,riskScore);
        } catch (SQLException ex) {
            System.err.println("Error with matching");
        }
        System.out.println("Matching complete");
    }
    public void matchApplication(int appNo){
        try {
            this.rules=getEnabledRules(this.db.getActiveRules());
            this.matches = new ArrayList();
            String ruleQuery;
            String ruleQuerySelect="SELECT APPRHS.APP_NO AS RHS ";
            for(Rule rule :rules){
                ruleQuery=null;
               
                List<String> conditions=rule.getConditions();
                
                if(conditions.size()>0){
                    //Based SQL
                    ruleQuery=" FROM APPLICATION APPLHS  INNER JOIN APPLICATION APPRHS ON APPLHS.REFERENCE<>APPRHS.REFERENCE ";
                    //Core Conditions
                    for(String condition : conditions){
                        switch(condition){
                            case "SAME_ADDRESS":
                                ruleQuery+=sameAddressCheck();
                                break;
                            case "SAME_PERSON":
                                ruleQuery+=samePersonCheck();
                                break;
                            case "SAME_BANK":
                                ruleQuery+=sameBankCheck();
                                break;
                            case "SAME_TELEPHONE":
                                ruleQuery+=sameTelephoneCheck();
                                break;
                            case "DIF_PERSON":
                                ruleQuery+=differentPersonCheck();
                                break;
                        }
                    }
                    ruleQuery=ruleQuery+" WHERE APPLHS.APP_NO="+appNo+" ";
                    //Other conditions
                    for(String condition:conditions){
                        switch(condition){
                            case "RHS_APP_FRD":
                                ruleQuery+=" AND APPRHS.FRAUD_STATUS='FRAUD'";
                                break;
                        }
                    }
                    for(String condition:conditions){
                        switch(condition){
                            case "MULTIPLE_RHS":
                                ruleQuery+=" AND APPLHS.APP_NO IN (SELECT APPLHS.APP_NO "+ruleQuery+" GROUP BY APPLHS.APP_NO HAVING COUNT(APPRHS.APP_NO)>1)";
                                break;
                        }
                    }
                    ruleQuery=ruleQuerySelect+ruleQuery;
                    
                    ResultSet rs;
                    System.out.println(ruleQuery);
                    rs = db.performMatching(ruleQuery);
                    while(rs.next()){
                        Match match;
                        match = new Match(appNo,rs.getInt("RHS"),rule);
                        this.matches.add(match);
                    }
                }
                else{
                    System.err.println("Error: "+rule.getName()+": No conditions found");
                }
            }
            if(matches==null){
                System.out.println(appNo+": No matches found");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MatchController.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    public List<Rule> getEnabledRules(ResultSet rs) throws SQLException{
        List<Rule> enabledRules;
        enabledRules=new ArrayList();
        
        while(rs.next()){
            Rule rule;
            rule = new Rule();
            rule.setRuleNo(rs.getInt("RULE_NO"));
            rule.setName(rs.getString("NAME"));
            rule.setType(rs.getString("TYPE"));
            rule.setDescription(rs.getString("DESCRIPTION"));
            rule.setScore(rs.getInt("SCORE"));
            rule.setDecision(rs.getString("DECISION"));
            rule.setConditions(db.getRuleConditions(rs.getInt("RULE_NO")));
            
            enabledRules.add(rule);
        }
        return enabledRules;
    }
    public int calculateRiskScore(List<Match> matches,int applicationNo) throws SQLException{
        int riskScore = 0;
        for(Match match : matches){
            Rule rule = match.getRule();
            riskScore+=rule.getScore();
        }
        db.setRiskScore(riskScore,applicationNo);
        return riskScore;
    }
    public void calculateRecommendation(int applicationNo, int riskScore) throws SQLException{
        String recommendation;
        
        boolean rDecision = false;
        boolean riskScoreDecision = true;
        boolean ruleDecision = true;
        boolean matchDecision = false;
        
        int referRiskScore = 400;

        if(rDecision==true){
            RManager rc = new RManager();
            rc.connect();
            //rc.getRecommendation(applicationNo);
            System.out.println("Application R CHECK COMPLETE");
        }
        if(riskScore>=referRiskScore){
            recommendation="REFER";
        }
        else{
            recommendation="APPROVE";
        }
        db.setRecommendation(recommendation,applicationNo);
    }
    public String samePersonCheck(){
        String sql=" INNER JOIN PERSON PERLHS ON APPLHS.APP_NO=PERLHS.APP_NO "
                + " INNER JOIN PERSON PERRHS ON "
                + " PERLHS.FIRST_NAME=PERRHS.FIRST_NAME AND PERLHS.SURNAME=PERRHS.SURNAME AND PERLHS.DOB=PERRHS.DOB"
                + " AND PERRHS.APP_NO=APPRHS.APP_NO "
                + " ";
        return sql;
    }
    public String sameAddressCheck(){
        String sameAddress="INNER JOIN ADDRESS ADDLHS ON APPLHS.APP_NO=ADDLHS.APP_NO "
                + "INNER JOIN ADDRESS ADDRHS ON "
                + "("
                    + "( ADDLHS.BUILDING_NUMBER=ADDRHS.BUILDING_NUMBER OR ADDLHS.BUILDING_NAME=ADDRHS.BUILDING_NAME)"
                    + " AND ((ADDLHS.POSTCODE=ADDRHS.POSTCODE) OR (ADDLHS.STREET=ADDRHS.STREET AND ADDLHS.POST_TOWN=ADDRHS.POSTCODE)) "
                + " AND ADDRHS.APP_NO=APPRHS.APP_NO "
                + ")";
        return sameAddress;
    }
    public String sameBankCheck(){
        String sql=" INNER JOIN BANK BNKLHS ON APPLHS.APP_NO=BNKLHS.APP_NO "
                + " INNER JOIN BANK BNKRHS ON "
                + " BNKLHS.SORT_CODE=BNKRHS.SORT_CODE AND BNKLHS.ACCOUNT_NUMBER=BNKRHS.ACCOUNT_NUMBER"
                + " AND APPRHS.APP_NO=BNKRHS.APP_NO "
                + " ";
        return sql;
    }
    public String sameTelephoneCheck(){
        String sql=" INNER JOIN TELEPHONE TELLHS ON TELLHS.APP_NO=APPLHS.APP_NO "
                + " INNER JOIN TELEPHONE TELRHS ON "
                + " TELLHS.TELEPHONE_NUMBER=TELRHS.TELEPHONE_NUMBER"
                + " AND APPRHS.APP_NO=TELRHS.APP_NO "
                + " ";
        return sql;
    }
    public String differentPersonCheck(){
        String sql=" INNER JOIN PERSON DPLHS ON APPLHS.APP_NO=DPLHS.APP_NO "
                + " INNER JOIN PERSON DPRHS ON "
                + " (DPLHS.FIRST_NAME<>DPRHS.FIRST_NAME OR DPLHS.SURNAME<>DPRHS.SURNAME OR DPLHS.DOB<>DPRHS.DOB)"
                + " AND DPRHS.APP_NO=APPRHS.APP_NO "
                + " ";
        return sql;
    }
    public String differentAddressCheck(){
        String sameAddress="INNER JOIN ADDRESS DADLHS ON APPLHS.APP_NO=DADLHS.APP_NO "
                + "INNER JOIN ADDRESS DADRHS ON "
                + "("
                    + "( DADLHS.BUILDING_NUMBER=DADRHS.BUILDING_NUMBER OR DADLHS.BUILDING_NAME=DADRHS.BUILDING_NAME)"
                    + " AND ((DADLHS.POSTCODE=DADRHS.POSTCODE) OR (DADLHS.STREET=DADRHS.STREET AND DADLHS.POST_TOWN=DADRHS.POSTCODE)) "
                + " AND DADRHS.APP_NO=APPRHS.APP_NO "
                + ")";
        return sameAddress;
    }
    public String differentBankCheck(){
        String sql=" INNER JOIN BANK DBNKLHS ON APPLHS.APP_NO=DBNKLHS.APP_NO "
                + " INNER JOIN BANK DBNKRHS ON "
                + " DBNKLHS.SORT_CODE=DBNKRHS.SORT_CODE AND DBNKLHS.ACCOUNT_NUMBER=DBNKRHS.ACCOUNT_NUMBER"
                + " AND DBNKRHS.APP_NO=APPRHS.APP_NO "
                + " ";
        return sql;
    }
    public String differentTelephoneCheck(){
        String sql=" INNER JOIN TELEPHONE TELLHS ON TELLHS.APP_NO=TELLHS.APP_NO "
                + " INNER JOIN TELEPHONE TELRHS ON "
                + " TELLHS.TELEPHONE_NUMBER<>TELRHS.TELEPHONE_NUMBER"
                + " AND APPRHS.APP_NO=TELRHS.APP_NO "
                + " ";
        return sql;
    }
}
