package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.models.Application;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ViewApplication extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        DBManager db = new DBManager();
        db.connect();
        Application application = getApplication(db,request.getParameter("reference"));

        if (application.getReference() == null) {
            request.setAttribute("errorMessage", "Error: Cannot find application");
            request.getRequestDispatcher("ApplicationSearch.jsp").forward(request, response);
        } else {
            request.setAttribute("application", application);
            try {
                request.setAttribute("matches", db.getApplicationMatches(application.getAppNo()));
            } catch (SQLException ex) {
                System.err.println("Error getting matches");
            }
            request.getRequestDispatcher("WEB-INF/Application.jsp").forward(request, response);
        }
        db.close();
    }

    private Application getApplication(DBManager db, String reference) {
        Application application = new Application();
        try {
            int appNo = db.getApplicationNoByReference(reference);
            if (appNo != -1) {
                application = db.getApplication(appNo);
                application.setMainApp(db.getMainApplicant(appNo));
                application.setCurrentAddress(db.getAddress(appNo));
                application.setBank(db.getBank(appNo));
                application.setTelephone(db.getContact(appNo));
            }
        } catch (SQLException ex) {
            application = null;
        }
        return application;
    }
}
