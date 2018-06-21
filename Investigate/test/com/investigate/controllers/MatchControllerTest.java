/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.controllers;

import com.investigate.entities.Rule;
import com.sun.istack.internal.logging.Logger;
import java.sql.ResultSet;
import java.util.List;
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
public class MatchControllerTest {
    DBManager db;
    public MatchControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of matchApplication method, of class MatchController.
     */
    @Test
    public void testMatchApplication() {
        db= new DBManager();
        db.connect();
        System.out.println("matchApplication");
        int appNo = 259;
        MatchController instance = new MatchController();
        instance.setupDatebase();
        instance.matchApplication(appNo);
        // TODO review the generated test code and remove the default call to fail.
        db.close();
    }

    /**
     * Test of getEnabledRules method, of class MatchController.
     */
    @Test
    public void testGetEnabledRules() throws Exception {
        System.out.println("getEnabledRules");
        //Setup
        db= new DBManager();
        db.connect();
        ResultSet rs = db.getActiveRules();
        MatchController instance = new MatchController();
        instance.setupDatebase();
        List<Rule> rules;
        rules=instance.getEnabledRules(rs);
    }
    
}
