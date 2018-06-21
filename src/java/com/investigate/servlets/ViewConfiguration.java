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

public class ViewConfiguration extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        DBManager db=new DBManager();
        try {
            db.connect();
            
            request.setAttribute("Configuration",db.getConfiguration());
            //System.out.println(db.getConfiguration().getScoreDecisionInt());
            request.getRequestDispatcher("Configuration.jsp").forward(request, response);
            
        } catch (SQLException ex) {
            Logger.getLogger(ViewConfiguration.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            db.close();
        }
    }
}
