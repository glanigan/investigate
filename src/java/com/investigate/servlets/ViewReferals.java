package com.investigate.servlets;

import com.investigate.controllers.StringConverter;
import com.investigate.controllers.DBManager;
import com.investigate.models.Application;
import com.investigate.models.Person;
import com.investigate.models.Product;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public final class ViewReferals extends HttpServlet {
    
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        DBManager db = new DBManager();
        try {
            db.connect();
            request.setAttribute("applications",db.getReferrals());
        } catch (SQLException ex) {
            Logger.getLogger(ViewReferals.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
          db.close();
          request.getRequestDispatcher("WEB-INF/Referrals.jsp").forward(request, response);  
        } 
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
