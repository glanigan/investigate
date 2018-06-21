package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateApplication extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DBManager db;
        db = new DBManager();
        db.connect();
        try {
            db.updateApplication(
                    request.getParameter("applicationNo"),
                    request.getParameter("investigationStatus"),
                    request.getParameter("fraudStatus"),
                    request.getParameter("decision")
            );
        } catch (SQLException ex) {
            Logger.getLogger(UpdateApplication.class.getName()).log(Level.SEVERE, null, ex);
        }

        db.close();
        
        request.setAttribute("title","Update Completed");
        request.setAttribute("message","Application has been updated successfully");
        request.getRequestDispatcher("messagePage.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
