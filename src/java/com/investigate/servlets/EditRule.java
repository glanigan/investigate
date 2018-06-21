/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.models.Rule;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditRule extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DBManager db;
        db = new DBManager();
        db.connect();
        try {
            Rule rule =db.getRule(Integer.parseInt(request.getParameter("ruleNo")));
            rule.setConditions(db.getRuleConditions(Integer.parseInt(request.getParameter("ruleNo"))));
            request.setAttribute("rule",rule);
            request.setAttribute("allConditions",db.getConditions());
            
        } catch (SQLException ex) {
            Logger.getLogger(UpdateRule.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        
        request.getRequestDispatcher("WEB-INF/Rule.jsp").forward(request, response);
        
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
