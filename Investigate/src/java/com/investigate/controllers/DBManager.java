/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.controllers;

import com.investigate.entities.Address;
import com.investigate.entities.Application;
import com.investigate.entities.Bank;
import com.investigate.entities.Match;
import com.investigate.entities.Person;
import com.investigate.entities.Rule;
import com.investigate.entities.Telephone;
import com.investigate.entities.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class DBManager {
    private final String URL = "jdbc:derby://localhost:1527/Risk Manager";
    private Connection dbConnection;
    public DBManager(){
    }
    public boolean connect(){      
        try {
            dbConnection = DriverManager.getConnection(URL,"rmApp", "rmApp");
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public User getUser(String username, String password){
        User user = null;
        try {
            PreparedStatement getUser= dbConnection.prepareStatement(
                    "SELECT Username, FROM User WHERE Username=? and password=?");
            
           getUser.setString(1,username);
           getUser.setString(2,password);
           getUser.executeQuery();
           ResultSet rs = getUser.executeQuery();
       
            while(rs.next()){
                user = new User();
                user.setUsername(rs.getString("Username"));
                user.setRole(rs.getString("Role"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        return user;
    }
    public boolean checkRef(String reference) throws SQLException{
        PreparedStatement checkRef= dbConnection.prepareStatement(
                    "SELECT 1 FROM APPLICATION A WHERE REFERENCE=?");
        ResultSet rs = checkRef.executeQuery();
        while(rs.next()){
            return false;
        }
        return true;
    }
    public void insertApplication(Application application){
        try {
            PreparedStatement insertApplication= dbConnection.prepareStatement(
                    "INSERT INTO APPLICATION(APP_NO,CREATION_DATE,CUSTOMER_NO,PRODUCT_NO,REFERENCE,APPLICATION_DATE,RISK_SCORE,INVESTIGATION_STATUS,FRAUD_STATUS)"
                            + "VALUES((SELECT CASE WHEN MAX(A.APP_NO) IS NULL THEN 100 ELSE MAX(A.APP_NO)+1 END FROM APPLICATION A),CURRENT_DATE,100,100,?,?,?,'UNWORKED',?)");
            
            insertApplication.setString(1,application.getReference());
            insertApplication.setDate(2,new java.sql.Date(application.getApplicationDate().getTime()));
            insertApplication.setInt(3, application.getRiskScore());
            insertApplication.setString(4,application.getFraudStatus());
            insertApplication.execute();
            System.out.println("Application Inserted");
        } catch (SQLException ex) {
            
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }       
    }
    public void insertPerson(int appNo,Person person) throws SQLException{
        PreparedStatement insertPerson= dbConnection.prepareStatement(
            "INSERT INTO Person(Person_No,APP_NO,TITLE,FIRST_NAME,SURNAME,DOB,GENDER,INCOME)"
                + "VALUES((SELECT CASE WHEN MAX(Person_No) IS NULL THEN 100 ELSE MAX(Person_No)+1 END FROM Person),?,?,?,?,?,?,?)");
        insertPerson.setInt(1,appNo);
        insertPerson.setString(2,person.getTitle());
        insertPerson.setString(3,person.getFirstname());
        insertPerson.setString(4,person.getSurname());
        insertPerson.setDate(5,new java.sql.Date(person.getDob().getTime()));
        insertPerson.setString(6,person.getGender());
        insertPerson.setDouble(7,person.getDoubleIncome());
        insertPerson.execute();
        System.out.println("Person Inserted");      
    }
    public void insertAddress(int appNo,Address address) throws SQLException{
        PreparedStatement insertAddress= dbConnection.prepareStatement(
                    "INSERT INTO Address(Address_No,APP_NO,TYPE,BUILDING_NAME,BUILDING_NUMBER,STREET,POST_TOWN,POSTCODE)"
                        + "VALUES((SELECT CASE WHEN MAX(Address_No) IS NULL THEN 100 ELSE MAX(Address_NO)+1 END FROM Address),?,?,?,?,?,?,?)");
        insertAddress.setInt(1,appNo);
        insertAddress.setString(2,"CURRENT");
        insertAddress.setString(3,address.getBuildingName());
        insertAddress.setString(4,address.getBuildingNumber());
        insertAddress.setString(5,address.getStreet());
        insertAddress.setString(6,address.getPostTown());
        insertAddress.setString(7,address.getPostcode());
        insertAddress.execute();
        System.out.println("Address Inserted");      
    }
    public void insertTelephone(int appNo,Telephone telephone) throws SQLException{
        PreparedStatement insertTelephone= dbConnection.prepareStatement(
                "INSERT INTO TELEPHONE(TELEPHONE_NO,APP_NO,TYPE,TELEPHONE_NUMBER)"
                    + "VALUES((SELECT CASE WHEN MAX(TELEPHONE_No) IS NULL THEN 100 ELSE MAX(TELEPHONE_NO)+1 END FROM TELEPHONE),?,?,?)");
        insertTelephone.setInt(1,appNo);
        insertTelephone.setString(2,telephone.getType());
        insertTelephone.setString(3,telephone.getNumber());
        insertTelephone.execute();
        System.out.println("Telephone inserted");
    }
    public void insertBank(int appNo,Bank bank) throws SQLException{
        PreparedStatement insertBank= dbConnection.prepareStatement(
                "INSERT INTO Bank(BANK_NO,APP_NO,SORT_CODE,ACCOUNT_NUMBER)"
                    + "VALUES((SELECT CASE WHEN MAX(BANK_No) IS NULL THEN 100 ELSE MAX(BANK_NO)+1 END FROM BANK),?,?,?)");
        insertBank.setInt(1,appNo);
        insertBank.setString(2,bank.getSortCode());
        insertBank.setString(3,bank.getAccountNumber());
        insertBank.execute(); 
        System.out.println("Bank inserted");
    }
    public void insertMatches(List<Match> matches) throws SQLException{
        PreparedStatement insertMatch = dbConnection.prepareStatement("INSERT INTO MATCHES(MATCH_NO,CREATION_DATE,LHS_APP_NO,RHS_APP_NO,RULE_NO)"
                + "VALUES((SELECT CASE WHEN MAX(MATCH_NO) IS NULL THEN 100 ELSE MAX(MATCH_NO)+1 END FROM MATCHES),CURRENT_DATE,?,?,?)");
        for(Match match : matches){
           insertMatch.setInt(1,match.getLhsApplicationNo());
           insertMatch.setInt(2,match.getRhsApplicationNo());
           Rule rule;
           rule=match.getRule();
           insertMatch.setInt(3,rule.getRuleNo());
           System.out.println("Insert Match to batch");
           insertMatch.addBatch();
       }
        insertMatch.executeBatch();
    }
    public ResultSet getReferals() throws SQLException{
        connect();
        ResultSet rs;
        PreparedStatement viewRefApps= dbConnection.prepareStatement(
           "SELECT * FROM ViewReferals");
        rs=viewRefApps.executeQuery();
        return rs;    
    }
    public int getApplicationNoByReference(String reference) throws SQLException{
        PreparedStatement viewAppRef= dbConnection.prepareStatement(
            "SELECT APP_NO FROM Application WHERE Reference=?");
        ResultSet rs;
        viewAppRef.setString(1,reference);
        rs = viewAppRef.executeQuery();
        int appNo = -1;
        while(rs.next()){
            appNo = rs.getInt("APP_NO");
            System.out.println(appNo);
        }
        return appNo;
    }
    public Application getApplication(int appNo) throws SQLException{
        Application application;
        
        PreparedStatement getApplication = dbConnection.prepareStatement("SELECT * "
                + "FROM APPLICATION A LEFT JOIN PRODUCT P ON A.PRODUCT_NO=P.PRODUCT_NO WHERE A.APP_NO=?");
        getApplication.setInt(1, appNo);
        ResultSet rs = getApplication.executeQuery();
        application = new Application();
        while(rs.next()){
            application.setProduct(rs.getString("DESCRIPTION"));
            application.setAppNo(rs.getInt("APP_NO"));
            application.setApplicationDate("APPLICATION_DATE");
            application.setReference(rs.getString("REFERENCE"));
            application.setValue(rs.getInt("Value"));
            application.setRecommendation(rs.getString("RECOMMENDATION"));
            application.setFraudStatus(rs.getString("FRAUD_STATUS"));
            application.setRiskScore(rs.getInt("RISK_SCORE"));
        }
        return application;
    }
    public Person getMainApplicant(int appNo) throws SQLException{
        Person person;
        
        PreparedStatement getMainApplicant = dbConnection.prepareStatement("SELECT * "
                + "FROM Person WHERE APP_NO=?");
        getMainApplicant.setInt(1, appNo);
        ResultSet rs = getMainApplicant.executeQuery();
        person = new Person();
        while(rs.next()){
            person.setFirstname(rs.getString("FIRST_NAME"));
            person.setSurname(rs.getString("SURNAME"));
            person.setDob(rs.getDate("DOB"));
            person.setGender(rs.getString("GENDER"));
            person.setIncome(rs.getDouble("INCOME"));
        }
        return person;
    }
    public Address getAddress(int appNo) throws SQLException{
        Address address = new Address();
        
        PreparedStatement getAddress = dbConnection.prepareStatement("SELECT * "
                + "FROM Address WHERE APP_NO=?");
        ResultSet rs;
        getAddress.setInt(1,appNo);
        rs = getAddress.executeQuery();
        System.out.println("test");
        while(rs.next()){
            address.setBuildingName(rs.getString("BUILDING_NAME"));
            address.setBuildingNumber(rs.getString("BUILDING_NUMBER"));
            address.setStreet(rs.getString("STREET"));
            address.setPostTown(rs.getString("POST_TOWN"));
            address.setPostcode(rs.getString("POSTCODE"));
        }
        return address;
    }
    public Bank getBank(int appNo) throws SQLException{
        Bank bank = new Bank();
        
        PreparedStatement getBank = dbConnection.prepareStatement("SELECT * "
                + "FROM BANK WHERE APP_NO=?");
        ResultSet rs;
        getBank.setInt(1,appNo);
        rs = getBank.executeQuery();

        while(rs.next()){
            bank.setSortCode(rs.getString("SORT_CODE"));
            bank.setAccountNumber(rs.getString("ACCOUNT_NUMBER"));
        }
        return bank;
    }
    public Telephone getTelephone(int appNo) throws SQLException{
        Telephone telephone= new Telephone();
        
        PreparedStatement getTelephone = dbConnection.prepareStatement("SELECT * "
                + "FROM TELEPHONE WHERE APP_NO=?");
        ResultSet rs;
        getTelephone.setInt(1,appNo);
        rs = getTelephone.executeQuery();

        while(rs.next()){
           telephone.setType(rs.getString("TYPE"));
           telephone.setNumber(rs.getString("TELEPHONE_NUMBER"));
        }
        return telephone;
    }
    
    public ResultSet getRules() throws SQLException{
        ResultSet rs;
        
        PreparedStatement getRules = dbConnection.prepareStatement("SELECT * FROM RULE");
        rs=getRules.executeQuery();
        
        return rs;
    }
    public ResultSet getActiveRules() throws SQLException{
        ResultSet rs;
        
        PreparedStatement getRules = dbConnection.prepareStatement("SELECT * FROM RULE WHERE ENABLED='Y'");
        rs=getRules.executeQuery();
        
        return rs;
    }
    public Rule getRule(int ruleNo) throws SQLException{
        Rule rule;
        rule=null;
        ResultSet rs;
        PreparedStatement getRules = dbConnection.prepareStatement("SELECT * FROM RULE WHERE RULE_NO=?");
        
        
        return rule;
    }
    public List<String> getRuleConditions(int ruleNo) throws SQLException{
        ResultSet rs;
        List<String> conditions = new ArrayList();

        PreparedStatement getRules = dbConnection.prepareStatement("SELECT NAME FROM CONDITION C INNER JOIN RULE_CONDITION RC ON C.CONDITION_NO=RC.CONDITION_NO AND RULE_NO=?");
        getRules.setInt(1,ruleNo);
        rs=getRules.executeQuery();
        while(rs.next()){
            conditions.add(rs.getString("NAME"));
        }
        return conditions;
    }
    public List<Match> getApplicationMatches(int appNo) throws SQLException{
        List<Match> matches;
        matches= new ArrayList();
        System.out.println("Statement prep");
        PreparedStatement getMatches = dbConnection.prepareStatement("SELECT * FROM VIEWAPPLICATIONMATCHES WHERE LHS_APP_NO=?");
        ResultSet rs;
        getMatches.setInt(1,appNo);
        rs = getMatches.executeQuery();
        
        while(rs.next()){
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
    public ResultSet performMatching(String sql) throws SQLException{
        ResultSet rs;
        PreparedStatement rule = dbConnection.prepareStatement(sql);
        rs = rule.executeQuery();
        return rs;
    }
    public void setRiskScore(int riskScore, int applicationNo) throws SQLException{
        PreparedStatement setRiskScore=dbConnection.prepareStatement("UPDATE APPLICATION SET RISK_SCORE=? WHERE APP_NO=?");
        setRiskScore.setInt(1,riskScore);
        setRiskScore.setInt(2,applicationNo);
        setRiskScore.execute();
    }
    public void setRecommendation(String recommendation, int applicationNo) throws SQLException{
        PreparedStatement setRecommendation=dbConnection.prepareStatement("UPDATE APPLICATION SET RECOMMENDATION=? WHERE APP_NO=?");
        setRecommendation.setString(1,recommendation);
        setRecommendation.setInt(2,applicationNo);
        setRecommendation.execute();
    }
    public void close(){
        try {
            dbConnection.close();
            dbConnection=null;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            dbConnection=null;
        }
    }   
}