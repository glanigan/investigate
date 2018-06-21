/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *///Password:26InVE$t1g@te$92
package com.investigate.controllers;

import com.investigate.models.Address;
import com.investigate.models.Application;
import com.investigate.models.Bank;
import com.investigate.models.Condition;
import com.investigate.models.Configuration;
import com.investigate.models.Match;
import com.investigate.models.Person;
import com.investigate.models.Product;
import com.investigate.models.Rule;
import com.investigate.models.Contact;
import com.investigate.models.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import com.mysql.jdbc.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {

    private final String URL = "jdbc:derby://localhost:1527/investigate";
    private Connection dbConnection;
    private Driver driver;

    public boolean connect() {
        try {
            driver = new Driver();
            dbConnection = DriverManager.getConnection(URL, "root", "root");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }


    public boolean checkRef(String reference) throws SQLException {
        PreparedStatement checkRef = dbConnection.prepareStatement(
                "SELECT 1 FROM APPLICATION A WHERE REFERENCE=?");
        checkRef.setString(1, reference);
        ResultSet rs = checkRef.executeQuery();
        while (rs.next()) {
            return false;
        }
        return true;
    }

    public void insertApplication(Application application) {
        try {
            PreparedStatement insertApplication = dbConnection.prepareStatement(
                    "INSERT INTO APPLICATION(APP_NO,CREATION_DATE,PRODUCT_NO,REFERENCE,APPLICATION_DATE,RISK_SCORE,INVESTIGATION_STATUS,FRAUD_STATUS)"
                    + "VALUES((SELECT CASE WHEN MAX(A.APP_NO) IS NULL THEN 100 ELSE MAX(A.APP_NO)+1 END FROM APPLICATION A),CURRENT_DATE,?,?,?,?,'UNWORKED',?)");
            insertApplication.setInt(1, application.getProduct().getProductNo());
            insertApplication.setString(2, application.getReference());
            insertApplication.setDate(3, Date.valueOf(application.getApplicationDate()));
            insertApplication.setInt(4, application.getRiskScore());
            insertApplication.setString(5, application.getFraudStatus());
            insertApplication.executeUpdate();

        } catch (SQLException ex) {

            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertPerson(int appNo, Person person) throws SQLException {
        PreparedStatement insertPerson = dbConnection.prepareStatement(
                "INSERT INTO PERSON(PERSON_NO,APP_NO,TITLE,FIRST_NAME,SURNAME,DOB,GENDER,INCOME)"
                + "VALUES((SELECT CASE WHEN MAX(P.PERSON_NO) IS NULL THEN 100 ELSE MAX(P.PERSON_NO)+1 END FROM PERSON P),?,?,?,?,?,?,?)");
        insertPerson.setInt(1, appNo);
        insertPerson.setString(2, person.getTitle());
        insertPerson.setString(3, person.getFirstname());
        insertPerson.setString(4, person.getSurname());
        insertPerson.setDate(5, Date.valueOf(person.getDob()));
        insertPerson.setString(6, person.getGender());
        insertPerson.setDouble(7, person.getDoubleIncome());
        System.out.println(insertPerson.toString());
        insertPerson.executeUpdate();
        System.out.println("Person Inserted");
    }

    public void insertAddress(int appNo, Address address) throws SQLException {
        PreparedStatement insertAddress = dbConnection.prepareStatement(
                "INSERT INTO ADDRESS(ADDRESS_NO,APP_NO,TYPE,BUILDING_NAME,BUILDING_NUMBER,STREET,POST_TOWN,POSTCODE)"
                + "VALUES((SELECT CASE WHEN MAX(A.ADDRESS_No) IS NULL THEN 100 ELSE MAX(A.ADDRESS_NO)+1 END FROM ADDRESS A),?,?,?,?,?,?,?)");
        insertAddress.setInt(1, appNo);
        insertAddress.setString(2, "CURRENT");
        insertAddress.setString(3, address.getBuildingName());
        insertAddress.setString(4, address.getBuildingNumber());
        insertAddress.setString(5, address.getStreet());
        insertAddress.setString(6, address.getPostTown());
        insertAddress.setString(7, address.getPostcode());
        insertAddress.executeUpdate();
    }

    public void insertContact(int appNo, Contact contact) throws SQLException {
        PreparedStatement insertTelephone = dbConnection.prepareStatement(
                "INSERT INTO CONTACT(CONTACT_NO,APP_NO,TYPE,TELEPHONE_NUMBER,EMAIL)"
                + "VALUES((SELECT CASE WHEN MAX(T.CONTACT_NO) IS NULL THEN 100 ELSE MAX(T.CONTACT_NO)+1 END FROM CONTACT T),?,?,?,?)");
        insertTelephone.setInt(1, appNo);
        insertTelephone.setString(2, contact.getType());
        insertTelephone.setString(3, contact.getNumber());
        insertTelephone.setString(4, contact.getEmail());
        insertTelephone.executeUpdate();
    }

    public void insertBank(int appNo, Bank bank) throws SQLException {
        PreparedStatement insertBank = dbConnection.prepareStatement(
                "INSERT INTO BANK(BANK_NO,APP_NO,SORT_CODE,ACCOUNT_NUMBER)"
                + "VALUES((SELECT CASE WHEN MAX(B.BANK_NO) IS NULL THEN 100 ELSE MAX(B.BANK_NO)+1 END FROM BANK B),?,?,?)");
        insertBank.setInt(1, appNo);
        insertBank.setString(2, bank.getSortCode());
        insertBank.setString(3, bank.getAccountNumber());
        insertBank.executeUpdate();
        System.out.println("Bank inserted");
    }

    public void insertMatches(List<Match> matches) throws SQLException {
        PreparedStatement insertMatch = dbConnection.prepareStatement(
                "INSERT INTO MATCHES(MATCH_NO,CREATION_DATE,LHS_APP_NO,RHS_APP_NO,RULE_NO)"
                + "VALUES((SELECT CASE WHEN MAX(M.MATCH_NO) IS NULL THEN 100 "
                + "ELSE MAX(M.MATCH_NO)+1 END FROM MATCHES M),CURRENT_DATE,?,?,?)");

        for (Match match : matches) {
            insertMatch.setInt(1, match.getLhsApplicationNo());
            insertMatch.setInt(2, match.getRhsApplicationNo());
            insertMatch.setInt(3, match.getRule().getRuleNo());
            insertMatch.addBatch();
        }
        insertMatch.executeBatch();
    }

    public List<Application> getReferrals() throws SQLException {
        PreparedStatement viewRefApps = dbConnection.prepareStatement("SELECT * FROM VIEWREFERALS");
        
        List<Application> applications = new ArrayList();
        ResultSet rs;
        rs = viewRefApps.executeQuery();
        while (rs.next()) {
            Application application = new Application();
            Person mainPerson = new Person();
            application.setAppNo(rs.getInt("APP_NO"));
            application.setProduct(new Product(0, null, null, rs.getString("DESCRIPTION")));
            application.setReference(rs.getString("REFERENCE"));
            application.setApplicationDate(rs.getDate("APPLICATION_DATE").toLocalDate());
            application.setRecommendation(StringConverter.toTitleCase(rs.getString("RECOMMENDATION")));
            application.setRiskScore(rs.getInt("RISK_SCORE"));
            mainPerson.setFirstname((rs.getString("FIRST_NAME")));
            mainPerson.setSurname(rs.getString("SURNAME"));
            application.setMainApp(mainPerson);
            applications.add(application);
        }
        return applications;
    }

    public int getApplicationNoByReference(String reference) throws SQLException {
        PreparedStatement viewAppRef = dbConnection.prepareStatement(
                "SELECT APP_NO FROM APPLICATION WHERE REFERENCE=?");
        ResultSet rs;
        viewAppRef.setString(1, reference);
        rs = viewAppRef.executeQuery();
        int appNo = -1;
        while (rs.next()) {
            appNo = rs.getInt("APP_NO");
        }
        return appNo;
    }

    public Application getApplication(int appNo) throws SQLException {
        Application application;

        PreparedStatement getApplication = dbConnection.prepareStatement("SELECT * "
                + "FROM APPLICATION A LEFT JOIN PRODUCT P ON A.PRODUCT_NO=P.PRODUCT_NO WHERE A.APP_NO=?");
        getApplication.setInt(1, appNo);
        ResultSet rs = getApplication.executeQuery();
        application = new Application();
        while (rs.next()) {
            application.setProduct(new Product(rs.getInt("PRODUCT_NO"), rs.getString("TYPE"), rs.getString("NAME"), rs.getString("DESCRIPTION")));
            application.setAppNo(rs.getInt("APP_NO"));
            application.setApplicationDate(rs.getString("APPLICATION_DATE"));
            application.setReference(rs.getString("REFERENCE"));
            application.setValue(rs.getInt("Value"));
            application.setRecommendation(rs.getString("RECOMMENDATION"));
            application.setDecision(rs.getString("DECISION"));
            application.setFraudStatus(rs.getString("FRAUD_STATUS"));
            application.setInvestigationStatus(rs.getString("INVESTIGATION_STATUS"));
            application.setRiskScore(rs.getInt("RISK_SCORE"));
        }
        return application;
    }

    public Person getMainApplicant(int appNo) throws SQLException {
        Person person;

        PreparedStatement getMainApplicant = dbConnection.prepareStatement("SELECT * "
                + "FROM PERSON WHERE APP_NO=?");
        getMainApplicant.setInt(1, appNo);
        ResultSet rs = getMainApplicant.executeQuery();
        person = new Person();
        while (rs.next()) {
            person.setFirstname(rs.getString("FIRST_NAME"));
            person.setSurname(rs.getString("SURNAME"));
            person.setDob(rs.getDate("DOB").toLocalDate());
            person.setGender(rs.getString("GENDER"));
            person.setIncome(rs.getDouble("INCOME"));
        }
        return person;
    }

    public Address getAddress(int appNo) throws SQLException {
        Address address = new Address();

        PreparedStatement getAddress = dbConnection.prepareStatement("SELECT * "
                + "FROM ADDRESS WHERE APP_NO=?");
        ResultSet rs;
        getAddress.setInt(1, appNo);
        rs = getAddress.executeQuery();
        while (rs.next()) {
            address.setBuildingName(rs.getString("BUILDING_NAME"));
            address.setBuildingNumber(rs.getString("BUILDING_NUMBER"));
            address.setStreet(rs.getString("STREET"));
            address.setPostTown(rs.getString("POST_TOWN"));
            address.setPostcode(rs.getString("POSTCODE"));
        }
        return address;
    }

    public Bank getBank(int appNo) throws SQLException {
        Bank bank = new Bank();

        PreparedStatement getBank = dbConnection.prepareStatement("SELECT * "
                + "FROM BANK WHERE APP_NO=?");
        ResultSet rs;
        getBank.setInt(1, appNo);
        rs = getBank.executeQuery();

        while (rs.next()) {
            bank.setSortCode(rs.getString("SORT_CODE"));
            bank.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
        }
        return bank;
    }

    public Contact getContact(int appNo) throws SQLException {
        Contact contact = new Contact();

        PreparedStatement getTelephone = dbConnection.prepareStatement("SELECT * "
                + "FROM CONTACT WHERE APP_NO=?");
        ResultSet rs;
        getTelephone.setInt(1, appNo);
        rs = getTelephone.executeQuery();

        while (rs.next()) {
            contact.setType(rs.getString("TYPE"));
            contact.setNumber(rs.getString("TELEPHONE_NUMBER"));
            contact.setEmail(rs.getString("EMAIL"));
        }
        return contact;
    }

    public ResultSet getRules() throws SQLException {
        ResultSet rs;

        PreparedStatement getRules = dbConnection.prepareStatement("SELECT * FROM RULE");
        rs = getRules.executeQuery();

        return rs;
    }

    public List<Rule> getEnabledRules() throws SQLException {
        ResultSet rs;

        PreparedStatement getRules = dbConnection.prepareStatement("SELECT * FROM RULE WHERE ENABLED='Y'");
        rs = getRules.executeQuery();

        List<Rule> enabledRules = new ArrayList();

        while (rs.next()) {
            Rule rule;
            rule = new Rule();
            rule.setRuleNo(rs.getInt("RULE_NO"));
            rule.setName(rs.getString("NAME"));
            rule.setType(rs.getString("TYPE"));
            rule.setDescription(rs.getString("DESCRIPTION"));
            rule.setScore(rs.getInt("SCORE"));
            rule.setDecision(rs.getString("DECISION"));
            rule.setConditions(getRuleConditions(rs.getInt("RULE_NO")));

            enabledRules.add(rule);
        }
        return enabledRules;
    }

    public List<Condition> getConditions() throws SQLException {
        ResultSet rs;
        List<Condition> conditions = new ArrayList();

        PreparedStatement getRules = dbConnection.prepareStatement("SELECT C.CONDITION_NO,C.NAME,C.DESCRIPTION,C.TYPE FROM CONDITIONS C");
        rs = getRules.executeQuery();
        while (rs.next()) {
            Condition condition = new Condition();
            condition.setConditionNo(rs.getInt("CONDITION_No"));
            condition.setName(rs.getString("NAME"));
            condition.setDescription(rs.getString("DESCRIPTION"));
            condition.setType(rs.getString("TYPE"));
            conditions.add(condition);
        }
        return conditions;
    }

    public Rule getRule(int ruleNo) throws SQLException {
        Rule rule = new Rule();
        ResultSet rs;
        PreparedStatement getRules = dbConnection.prepareStatement("SELECT * FROM RULE WHERE RULE_NO=?");
        getRules.setInt(1, ruleNo);
        rs = getRules.executeQuery();
        while (rs.next()) {
            rule.setRuleNo(rs.getInt("RULE_NO"));
            rule.setName(rs.getString("NAME"));
            rule.setDescription(rs.getString("DESCRIPTION"));
            rule.setScore(rs.getInt("SCORE"));
            rule.setEnabled(rs.getString("ENABLED").charAt(0));
        }
        return rule;
    }

    public Configuration getConfiguration() throws SQLException {
        PreparedStatement getConfiguration = dbConnection.prepareStatement("SELECT *"
                + "FROM CONFIGURATION");
        ResultSet rs;
        rs = getConfiguration.executeQuery();
        while (rs.next()) {
            return new Configuration(
                    rs.getBoolean("RDECISION"),
                    rs.getBoolean("MATCHDECISION"),
                    rs.getInt("SCOREDECISION")
            );
        }
        return null;
    }

    public void updateRule(Rule rule) {
        try {
            PreparedStatement updateRule = dbConnection.prepareStatement("UPDATE RULE SET DESCRIPTION=?,SCORE=?,ENABLED=? WHERE RULE_NO=?");
            updateRule.setString(1, rule.getDescription());
            updateRule.setInt(2, rule.getScore());
            updateRule.setBoolean(3, rule.getEnabled());
            updateRule.setInt(4, rule.getRuleNo());
            
            for(Condition condition: rule.getConditions()){
                
                
                break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Condition> getRuleConditions(int ruleNo) throws SQLException {
        ResultSet rs;
        List<Condition> conditions = new ArrayList();

        PreparedStatement getRules = dbConnection.prepareStatement("SELECT * FROM CONDITIONS C INNER JOIN RULE_CONDITION RC ON C.CONDITION_NO=RC.CONDITION_NO AND RULE_NO=?");
        getRules.setInt(1, ruleNo);
        rs = getRules.executeQuery();
        while (rs.next()) {
            Condition condition = new Condition();
            condition.setName(rs.getString("NAME"));
            condition.setDescription(rs.getString("DESCRIPTION"));
            condition.setType(rs.getString("TYPE"));
            condition.setQuery(rs.getString("QUERY"));
            condition.setParameter(rs.getString("PARAMETER"));
            conditions.add(condition);
        }
        return conditions;
    }

    public List<Product> getProducts() {
        List<Product> products = new ArrayList();
        try {
            ResultSet rs;

            PreparedStatement getProducts = dbConnection.prepareStatement("SELECT * FROM PRODUCT");
            rs = getProducts.executeQuery();
            while (rs.next()) {
                products.add(new Product(rs.getInt("PRODUCT_NO"), rs.getString("TYPE"), rs.getString("NAME"), rs.getString("DESCRIPTION")));
            }
            return products;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return products;
    }

    public List<Match> getApplicationMatches(int appNo) throws SQLException {
        List<Match> matches;
        matches = new ArrayList();
        PreparedStatement getMatches = dbConnection.prepareStatement("SELECT * FROM VIEWAPPLICATIONMATCHES WHERE LHS_APP_NO=?");
        ResultSet rs;
        getMatches.setInt(1, appNo);
        rs = getMatches.executeQuery();

        while (rs.next()) {
            Rule rule = new Rule();
            rule.setType(rs.getString("RULE_TYPE"));
            rule.setName(rs.getString("RULE"));
            rule.setScore(rs.getInt("SCORE"));

            Match match;
            match = new Match();
            match.setReferenceRHS(rs.getString("RHS_REF"));
            match.setRule(rule);
            matches.add(match);
        }
        return matches;
    }

    public ResultSet performMatching(String sql) throws SQLException {
        ResultSet rs;
        PreparedStatement rule = dbConnection.prepareStatement(sql);
        rs = rule.executeQuery();
        return rs;
    }

    public void setRiskScore(int riskScore, int applicationNo) throws SQLException {
        PreparedStatement setRiskScore = dbConnection.prepareStatement("UPDATE APPLICATION "
                + "SET RISK_SCORE=? WHERE APP_NO=?");
        setRiskScore.setInt(1, riskScore);
        setRiskScore.setInt(2, applicationNo);
        setRiskScore.execute();
    }

    public void setRecommendation(String recommendation, int applicationNo) throws SQLException {
        PreparedStatement setRecommendation = dbConnection.prepareStatement("UPDATE APPLICATION SET RECOMMENDATION=? WHERE APP_NO=?");
        setRecommendation.setString(1, recommendation);
        setRecommendation.setInt(2, applicationNo);
        setRecommendation.execute();
    }

    public void close() {
        try {
            dbConnection.close();
            dbConnection = null;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            dbConnection = null;
        }
    }

    public void updateConfiguration(Configuration configuration) throws SQLException {
        PreparedStatement updateConfiguration = dbConnection.prepareStatement(
                "UPDATE CONFIGURATION SET RDECISION=?,SCOREDECISION=?,MATCHDECISION=?");
        
        updateConfiguration.setBoolean(1, configuration.isrDecision());
        updateConfiguration.setInt(2, configuration.getScoreDecisionInt());
        updateConfiguration.setBoolean(3, configuration.isMatchDecision());
        
        updateConfiguration.execute();
    }

    public void updateApplication(String appNo, String investigationStatus, String fraudStatus, String decision)throws SQLException {
        PreparedStatement updateApplication = dbConnection.prepareStatement (
                "UPDATE APPLICATION SET INVESTIGATION_STATUS=?,FRAUD_STATUS=?,DECISION=? WHERE APP_NO=?"
        );
        updateApplication.setString(1,investigationStatus);
        updateApplication.setString(2,fraudStatus);
        updateApplication.setString(3,decision);
        updateApplication.setString(4,appNo);
        
        updateApplication.execute();
        System.out.print(appNo+":"+investigationStatus+":"+fraudStatus+":"+decision+":");
    }
}
