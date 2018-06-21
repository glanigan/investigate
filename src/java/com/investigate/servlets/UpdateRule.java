/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.models.Rule;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateRule extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        DBManager db;
        db = new DBManager();
        db.connect();
        Rule rule = new Rule();
        rule.setRuleNo(Integer.parseInt(request.getParameter("ruleNo")));
        rule.setName(request.getParameter("name"));
        rule.setDecision(request.getParameter("description"));
        rule.setScore(Integer.parseInt(request.getParameter("score")));
        rule.setEnabled(Boolean.parseBoolean(request.getParameter("enabled")));
        Map m =request.getParameterMap();
        m.values();
        //db.updateRule(rule);
        db.close();
    }
}
