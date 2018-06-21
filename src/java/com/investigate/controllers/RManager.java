package com.investigate.controllers;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

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
    public String getRecommendation(int applicationNo){
        String decision;
        try{
            connection.eval("source('C://Users/garyl//Desktop//test.R')");
            decision=connection.eval("getDecision("+applicationNo+")").asString();
            connection.close();
        } catch (RserveException | REXPMismatchException ex) {
            System.err.println("Cannot get recommendation");
            return "PENDING";
        }
        return decision;
    }
}
