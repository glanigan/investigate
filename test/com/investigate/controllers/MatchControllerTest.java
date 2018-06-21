/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.controllers;

import com.investigate.models.Rule;
import java.sql.ResultSet;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        int appNo = 222222222;
        MatchEngine instance = new MatchEngine();
        //instance.setupDatebase();
        instance.runMatching(appNo);
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
        MatchEngine instance = new MatchEngine();
        instance.setupDatebase();
    }
    
}
