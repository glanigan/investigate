/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.entities.Application;
import com.investigate.entities.Person;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 *
 * @author garyl
 */
public class ViewReferals extends HttpServlet {
    private String url="jdbc:derby://localhost:1527/Risk Manager";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("Process ViewReferals");
        List<Application> applications = new ArrayList<Application>();
        applications = getReferals();
        request.setAttribute("applications", applications);
        request.getRequestDispatcher("WEB-INF/Referals.jsp").forward(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Post");
        processRequest(request, response);
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException{
        System.out.println("Get");
        processRequest(request, response);
    }
    private List<Application> getReferals(){
        List<Application> applications = new ArrayList<Application>();
        DBManager db;
        try {
            ResultSet rs;
            db=new DBManager();
            rs = db.getReferals();
            while(rs.next()) {
                Application application = new Application();
                application.setAppNo(rs.getInt("APP_NO"));
                application.setProduct(rs.getString("NAME"));
                application.setReference(rs.getString("REFERENCE"));
                application.setApplicationDate(rs.getDate("APPLICATION_DATE"));
                application.setRecommendation(rs.getString("RECOMMENDATION"));
                application.setRiskScore(rs.getInt("RISK_SCORE"));
               
                Person mainPerson = new Person();
                mainPerson.setFirstname(rs.getString("FIRST_NAME"));
                mainPerson.setSurname(rs.getString("SURNAME"));
                application.setMainApp(mainPerson);
                        
                applications.add(application);
            }
            db.close();
        } catch (SQLException ex) {
            System.err.println("Error");
        }
        return applications;
    }   
    @Override
    public String getServletInfo() {
        return "Short description";
    }    
}
