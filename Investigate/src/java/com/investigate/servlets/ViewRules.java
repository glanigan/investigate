/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.entities.Rule;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author garyl
 */
@WebServlet(name = "ViewRules", urlPatterns = {"/ViewRules"})
public class ViewRules extends HttpServlet {
    private String url="jdbc:derby://localhost:1527/Risk Manager";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Process ViewReferals");
        List<Rule> rules = new ArrayList<Rule>();
        rules = getRules();
        request.setAttribute("rules", rules);
        request.getRequestDispatcher("/WEB-INF/ViewRules.jsp").forward(request, response);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    private List<Rule> getRules(){
        List<Rule> rules = new ArrayList<Rule>();
        try {
            ResultSet rs;
            DBManager db = new DBManager();
            db.connect();
            rs = db.getRules();
            while(rs.next()) {
                Rule rule;
                rule = new Rule(
                        rs.getInt("Rule_No"),
                        rs.getString("Type"),
                        rs.getString("Name"),
                        rs.getString("Description"),
                        rs.getString("Decision"),
                        rs.getInt("Score")
                );
                if(rs.getString("Enabled")!=null){
                    if(rs.getString("Enabled").equals("Y")==true){
                        rule.setEnabled('Y');
                    }
                    else{
                        rule.setEnabled('N');
                    }
                }else{
                    rule.setEnabled('N');
                }
                rules.add(rule);
            }
            db.close();
        } catch (SQLException ex) {
            System.err.println("Error");
        }
        return rules;
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
