/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.controllers.MatchEngine;
import com.investigate.models.Address;
import com.investigate.models.Application;
import com.investigate.models.Bank;
import com.investigate.models.Person;
import com.investigate.models.Product;
import com.investigate.models.Contact;
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
public class LoadApplication extends HttpServlet {

    private int appNo;

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        boolean successful = false;
        Application application;
        Person applicant;
        Address address;
        Bank bank;
        Contact contact;

        application = new Application();

        application.setReference(request.getParameter("appRef").trim());
        application.setProduct(new Product(Integer.parseInt(request.getParameter("product"))));
        application.setApplicationDate(request.getParameter("AppDate"));
        application.setFraudStatus(request.getParameter("applicationFraudStatus"));
        if(request.getParameter("applicationValue").length()>=1){
            application.setValue(Double.parseDouble(request.getParameter("applicationValue").trim()));
        }
        
        applicant = new Person();
        if (request.getParameter("title").contains("NULL") == false) {
            applicant.setTitle(request.getParameter("title"));
        }
        applicant.setFirstname(request.getParameter("firstname").toUpperCase().trim());
        applicant.setSurname(request.getParameter("surname").toUpperCase().trim());
        applicant.setDob(request.getParameter("dob"));
        applicant.setGender(request.getParameter("gender"));
        if(request.getParameter("income")!=null && request.getParameter("income").length()>=1){
            applicant.setIncome(Double.parseDouble(request.getParameter("income")));
        }
        
        address = new Address();
        address.setBuildingName(request.getParameter("buildingName").toUpperCase().trim());
        address.setBuildingNumber(request.getParameter("buildingNumber").toUpperCase().trim());
        address.setStreet(request.getParameter("street").toUpperCase().trim());
        address.setPostTown(request.getParameter("postTown").toUpperCase().trim());
        address.setPostcode(request.getParameter("postcode").toUpperCase().trim());
        address.setResidentalStatus(request.getParameter("residentialStatus").toUpperCase().trim());
        
        bank = new Bank();
        bank.setSortCode(request.getParameter("sortCode"));
        bank.setAccountNumber(request.getParameter("accountNumber"));
        
        contact = new Contact();
        contact.setType(request.getParameter("telephoneType"));
        contact.setNumber(request.getParameter("telephoneNumber"));
        contact.setEmail(request.getParameter("email"));
        
        try {
            DBManager db;
            db = new DBManager();
            db.connect();
            if (!db.checkRef(application.getReference())) {
                System.err.println("Error - Application with current Reference");
                db.close();
            } else {
                db.insertApplication(application);
                appNo = db.getApplicationNoByReference(application.getReference());
                db.insertPerson(appNo, applicant);
                db.insertAddress(appNo, address);
                if (bank.getSortCode().isEmpty() != true || bank.getAccountNumber().isEmpty() != true) {
                    db.insertBank(appNo, bank);
                }
                if (contact.getType().isEmpty() != true || contact.getNumber().isEmpty() != true) {
                    db.insertContact(appNo, contact);
                }
                successful=true;
                db.close();
            }
        } catch (SQLException ex) {
            System.err.println("Connection Failed");
        }

        if (successful) {
            responseSuccessful(request,response);
            MatchEngine mc;
            mc = new MatchEngine();
            mc.runMatching(appNo);
        } else {
            responseFailed(response, "Failed");
        }
    }

    private void responseSuccessful(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("title","Load Completed");
        request.setAttribute("message","Application has been loaded to the system successfully");
        request.getRequestDispatcher("messagePage.jsp").forward(request, response);
    }

    private void responseFailed(HttpServletResponse response, String message) throws ServletException, IOException {
        response.addHeader("error", message);
        response.sendRedirect("CreateApplication.jsp");
    }
}
