/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.controllers;

import java.util.logging.Level;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 *
 * @author garyl
 */
public class RManager {
    private RConnection connection;
    
    public RManager(){
        connection=null;
    }
    public boolean connect(){
        connection=null;
        try {
             connection = new RConnection();
             return true;
         } catch (RserveException e) {
             System.out.println("Connect: Application cannot connect to R Server");
             connection=null;
             return false;
         }
    }
    public int getRiskscore(){
        return 0;
    } 
    public String getRecommendation(int applicationNo){
        String decision;
        try{
            connection.eval("source('C://Users/garyl//Desktop//test.R')");
            decision=connection.eval("getDecision("+applicationNo+")").asString();
            
        } catch (RserveException | REXPMismatchException ex) {
            System.err.println("Cannot get recommendation");
            return "PENDING";
        }
        return decision;
    }
}
