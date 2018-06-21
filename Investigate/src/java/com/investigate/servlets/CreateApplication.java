/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.servlets;

import com.investigate.controllers.DBManager;
import com.investigate.controllers.MatchController;
import com.investigate.entities.Address;
import com.investigate.entities.Application;
import com.investigate.entities.Bank;
import com.investigate.entities.Person;
import com.investigate.entities.Telephone;
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
public class CreateApplication extends HttpServlet {
    
    private int appNo;
    private Application application;
    private Person applicant;
    private Address address;
    private Bank bank;
    private Telephone telephone;
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        //Application Details
        application = new Application();
        application.setReference(request.getParameter("appRef"));
        application.setApplicationDate(request.getParameter("AppDate"));
        application.setFraudStatus(request.getParameter("applicationFraudStatus"));
        //application.setValue(request.getParameter("applicationValue").trim());
        
        applicant = new Person();
            if(request.getParameter("title").contains("NULL")==false){
                applicant.setTitle(request.getParameter("title"));
            }
            applicant.setFirstname(request.getParameter("firstname").toUpperCase().trim());
            applicant.setSurname(request.getParameter("surname").toUpperCase().trim());
            applicant.setDob(request.getParameter("dob"));
            applicant.setGender(request.getParameter("gender"));
        try{
            applicant.setIncome(Double.parseDouble(request.getParameter("income")));
            System.out.println(applicant.getIncome());
        }catch(Exception e){
            System.out.println("Can't parse Income");
        }
        address = new Address();
            address.setBuildingName(request.getParameter("buildingName").toUpperCase().trim());
            address.setBuildingNumber(request.getParameter("buildingNumber").toUpperCase().trim());
            address.setStreet(request.getParameter("street").toUpperCase().trim());
            address.setPostTown(request.getParameter("postTown").toUpperCase().trim());
            address.setPostcode(request.getParameter("postcode").toUpperCase().trim());
        bank = new Bank();
            bank.setSortCode(request.getParameter("sortCode"));
            bank.setAccountNumber(request.getParameter("accountNumber"));
        telephone = new Telephone();
            telephone.setType(request.getParameter("telephoneType"));
            telephone.setNumber(request.getParameter("telephoneNumber"));
        try{
            DBManager db;
            db = new DBManager();
            db.connect();
            /*
            if(db.checkRef(application.getReference())==false){
                System.out.println("Error - Application with current Reference");
                db.close();
                responseFail(response);
            }*/
            db.insertApplication(application);
            System.out.println("Application inserted");
            appNo = db.getApplicationNoByReference(application.getReference());
            db.insertPerson(appNo,applicant);
            db.insertAddress(appNo,address);
            
            if(bank.getSortCode().isEmpty()!=true || bank.getAccountNumber().isEmpty()!=true){
                System.out.println("bank");
                db.insertBank(appNo,bank);
            }
            if(telephone.getType().isEmpty()!=true || telephone.getNumber().isEmpty()!=true){
                System.out.println("telephone");
                db.insertTelephone(appNo, telephone);
            }
            db.close();
        } catch (SQLException ex) {
            System.out.println("Connection Failed");
        }
        response.sendRedirect("LoadSuccessful.jsp");
                
        MatchController mc;
        mc = new MatchController();
        mc.runMatching(appNo); 
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        processRequest(request, response);
    }
    private void responseSuccessful(HttpServletResponse response) throws ServletException, IOException{
        response.sendRedirect("LoadSuccessful.jsp");
    }       
    private void responseFail(HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("Error.jsp");
    }
}
