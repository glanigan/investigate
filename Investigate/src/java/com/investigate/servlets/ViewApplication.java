/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.entities.Application;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author garyl
 */
public class ViewApplication extends HttpServlet {
    private int appNo;
    private String findapp;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Application application;

        application = getApplication();

        if(application.getReference()==null){
            System.out.println("Application not found");
            request.setAttribute("errorMessage", "Error: Cannot find application");
            request.getRequestDispatcher("ApplicationSearch.jsp").forward(request, response);
        }
        else{
            request.setAttribute("application", application);
            
            DBManager db = new DBManager();
            db.connect();
            
            try {
                System.out.println(application.getAppNo());
                request.setAttribute("matches",db.getApplicationMatches(application.getAppNo()));
            } catch (SQLException ex) {
                System.out.println("Error getting matches");
            }
            request.getRequestDispatcher("WEB-INF/Application.jsp").forward(request, response);
        }
        
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.findapp=getURLApplicationNo(request.getRequestURI());
        this.appNo=0;
        System.out.println("Get "+findapp);
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("POST");
        this.findapp=request.getParameter("reference");
        processRequest(request, response);
    }
    private String getURLApplicationNo(String url){
        url=url.substring(url.lastIndexOf("/"));
        url = url.substring(1);
        return url;
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
    private Application getApplication(){
        Application application = new Application();
        try{
            DBManager db = new DBManager();
            db.connect();
            appNo = db.getApplicationNoByReference(findapp);
            if(appNo!=-1){
                application = db.getApplication(appNo);
                application.setMainApp(db.getMainApplicant(appNo));
                application.setCurrentAddress(db.getAddress(appNo));
                application.setBank(db.getBank(appNo));
                application.setTelephone(db.getTelephone(appNo));
            }
        }catch(SQLException ex){
            System.out.println("Fatal load error");
            application=null;
        }
        return application;
    }  
}
