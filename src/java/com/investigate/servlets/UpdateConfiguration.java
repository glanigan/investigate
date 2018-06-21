package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.models.Configuration;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateConfiguration extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        boolean rDecision = request.getParameter("rDecision")!=null;
        boolean scoreDecision = request.getParameter("scoreDecision")!=null;
        boolean matchDecision = request.getParameter("matchDecision")!=null;
        int riskScore;
        if(scoreDecision==false) riskScore=0;
        
        else riskScore=Integer.parseInt(request.getParameter("scoreDecisionValue"));
        
        DBManager db = new DBManager();
        db.connect();
        try {
            db.updateConfiguration(new Configuration(rDecision,matchDecision,riskScore));
        } catch (SQLException ex) {
            Logger.getLogger(UpdateConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }
        db.close();
        request.setAttribute("title","Update Completed");
        request.setAttribute("message","Configuration has been updated successfully");
        request.getRequestDispatcher("messagePage.jsp").forward(request, response);
    }
}
