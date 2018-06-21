/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.investigate.controllers.DBManager;
import com.investigate.entities.Application;
import com.investigate.entities.Person;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author garyl
 */
public class Refresh {
    private final String URL = "jdbc:derby://localhost:1527/Risk Manager";
    private Connection dbConnection;
    public Refresh() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        try {
            dbConnection = DriverManager.getConnection(URL,"rmApp", "rmApp");
            
        } catch (SQLException ex) {
            Logger.getLogger(Refresh.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void refresh() throws SQLException {
        /*
        PreparedStatement dropViewApplication = dbConnection.prepareStatement("DROP View ViewApplication");
        PreparedStatement dropViewReferals = dbConnection.prepareStatement("DROP View ViewApplicationReferals");
        PreparedStatement dropViewRules = dbConnection.prepareStatement("DROP View ViewRules");
        
        PreparedStatement dropTableApplication = dbConnection.prepareStatement("DROP TABLE APPLICATION");
        PreparedStatement dropTablePerson = dbConnection.prepareStatement("DROP TABLE Person");
        PreparedStatement dropTableAddress = dbConnection.prepareStatement("DROP TABLE Address");
        PreparedStatement dropTableBank = dbConnection.prepareStatement("DROP TABLE BANK");
        PreparedStatement dropTableProduct = dbConnection.prepareStatement("DROP TABLE Product");
        
        PreparedStatement dropTableRule = dbConnection.prepareStatement("DROP TABLE Rule");
        PreparedStatement dropTableMatches = dbConnection.prepareStatement("DROP TABLE Matches");
        PreparedStatement dropTableCondition = dbConnection.prepareStatement("DROP TABLE Condition");
        PreparedStatement dropTableCustomer = dbConnection.prepareStatement("DROP TABLE Customer");
        */
        PreparedStatement createTableUser = dbConnection.prepareStatement("CREATE TABLE Users("
                + "User_No int PRIMARY KEY,"
                + "Username varchar(40) NOT NULL,"
                + "Password varchar(40) NOT NULL,"
                + "Role varchar(40),"
                + "Customer_No int )"
        );
        PreparedStatement createTableCustomer = dbConnection.prepareStatement("CREATE TABLE Customer("
                + "Customer_No int PRIMARY KEY,"
                + "Sector varchar(50),"
                + "Name varchar(50),"
                + "Description varchar(50))"
        );
        PreparedStatement createTableProduct = dbConnection.prepareStatement("CREATE TABLE Product("
                + "Product_No int PRIMARY KEY,"
                + "Customer_No int,"
                + "Type varchar(50),"
                + "Name varchar(50),"
                + "Description varchar(50))"
        );
        PreparedStatement createTableApplication = dbConnection.prepareStatement("CREATE TABLE APPLICATION("
                + "App_No INT NOT NULL PRIMARY KEY,"
                + "Creation_Date date NOT NULL,"
                + "Customer_No INT NOT NULL,"
                + "Product_No INT NOT NULL,"
                + "Reference VARCHAR(40) NOT NULL,"
                + "Application_Date DATE NOT NULL,"
                + "Version_No INT NOT NULL DEFAULT 1,"
                + "Current_Version char DEFAULT 'Y',"
                + "Value INT,"
                + "Decision varchar(40) DEFAULT 'APPROVED',"
                + "Recommendation varchar(40) DEFAULT 'PENDING',"
                + "Risk_Score int,"
                + "INVESTIGATION_STATUS VARCHAR(30),"
                + "FRAUD_STATUS VARCHAR(30))");
        PreparedStatement createTablePerson = dbConnection.prepareStatement("CREATE TABLE Person("
                + "Person_No INT NOT NULL PRIMARY KEY,"
                + "App_No INT NOT NULL,"
                + "Type varchar(40) NOT NULL DEFAULT 'MAIN APPLICATION',"
                + "Title varchar(10),"
                + "First_Name varchar(50),"
                + "Middle_Name varchar(50),"
                + "Surname varchar(50),"
                + "DOB date,"
                + "Gender char DEFAULT 'U',"
                + "Income int,"
                + "FRAUD_STATUS varchar(24) DEFAULT 'CLEAR')");
        PreparedStatement createTableAddress = dbConnection.prepareStatement("CREATE TABLE Address("
                + "Address_No INT NOT NULL PRIMARY KEY,"
                + "App_No INT NOT NULL,"
                + "Type varchar(40) NOT NULL DEFAULT 'CURRENT',"
                + "Building_Name varchar(64),"
                + "Building_Number varchar(30),"
                + "Street varchar(64),"
                + "Post_Town varchar(64),"
                + "Postcode varchar(10),"
                + "FRAUD_STATUS varchar(24) DEFAULT 'CLEAR')");
        PreparedStatement createTableRule = dbConnection.prepareStatement("CREATE TABLE Rule("
                + "Rule_No int NOT NULL PRIMARY KEY,"
                + "Type varchar(40),"
                + "Customer_No int,"
                + "Name varchar(30) NOT NULL,"
                + "Description varchar(126),"
                + "Decision varchar(30),"
                + "Score int NOT NULL,"
                + "Enabled char NOT NULL)");
        PreparedStatement createTableRuleCondition = dbConnection.prepareStatement("CREATE TABLE Rule_Condition(" +
            "    RULE_NO INT NOT NULL," +
            "    CONDITION_NO INT NOT NULL)");
        PreparedStatement createTableCondition = dbConnection.prepareStatement("CREATE TABLE Condition(" +
            "    CONDITION_NO INT NOT NULL PRIMARY KEY," +
            "    Name VARCHAR(30) NOT NULL," +
            "    DESCRIPTION VARCHAR(124))");
        PreparedStatement createTableMatches = dbConnection.prepareStatement("CREATE TABLE MATCHES("+
            "    MATCH_NO INT NOT NULL PRIMARY KEY," +
            "    CREATION_DATE DATE NOT NULL," +
            "    LHS_APP_NO INT NOT NULL," +
            "    RHS_APP_NO INT NOT NULL," +
            "    RULE_NO INT NOT NULL)");
        PreparedStatement createTableBank = dbConnection.prepareStatement("CREATE TABLE BANK(" +
            "BANK_NO INT NOT NULL PRIMARY KEY, " +
            "APP_NO INT NOT NULL, " +
            "SORT_CODE VARCHAR(8), " +
            "ACCOUNT_NUMBER INT, " +
            "BRANCH VARCHAR(34))");
        PreparedStatement createTableTelephone = dbConnection.prepareStatement("CREATE TABLE TELEPHONE("+
            "TELEPHONE_NO INT NOT NULL PRIMARY KEY, " 
                + "APP_NO INT NOT NULL," 
                + "TYPE VARCHAR(24)," 
                + "TELEPHONE_NUMBER VARCHAR(36))");
        
       // dropViewApplication.execute();
        //dropViewReferals.execute();
        //dropViewRules.execute();
        /*dropTableMatches.execute();
        dropTableApplication.execute();
        dropTablePerson.execute();
        dropTableAddress.execute();
        dropTableBank.execute();
        dropTableCondition.execute();
        dropTableRule.execute();
        dropTableProduct.execute();
        dropTableCustomer.execute();*/
        createTableUser.execute();
        createTableCustomer.execute();
        createTableProduct.execute();
        createTableApplication.execute();
        createTablePerson.execute();
        createTableAddress.execute();
        createTableBank.execute();
        createTableTelephone.execute();
        createTableRule.execute();
        createTableRuleCondition.execute();
        createTableCondition.execute();
        createTableMatches.execute();
    }
}
