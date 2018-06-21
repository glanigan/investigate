/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author garyl
 */
public class Person {
    private enum Title{
        MR,MISS,MRS,DR
    }
    private enum Gender{
        MALE,FEMALE,OTHER,UNKNOWN
    }
    private String title;
    private String firstname;
    private String middlename;
    private String surname;
    private Date dob;
    private String gender;
    private double income;
    
    public Person(){}
    public void setTitle(String title){
        this.title=title;
    }
    public String getTitle(){
        return this.title;
    }
    public String getFirstname(){
        return this.firstname;
    }
    public void setFirstname(String firstname){
        this.firstname=firstname;
    }
    public String getSurname(){
        return this.surname;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    public Date getDob(){
        return this.dob;
    }
    public String getStringDob(){
        try{
            SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
            return formatter.format(this.dob);
        }catch(Exception e){
            return null;
        }  
    }
    public void setDob(String dob){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yyyy");
        try {
            System.out.println(dob);
            this.dob=formatter.parse(dob);
            System.out.println(this.dob);
        } catch (ParseException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setDob(Date dob){
        this.dob=dob;
    }
    public String getFullname(){
        return firstname+" "+surname;
    }
    public String getGender(){
        return this.gender;
    }
    public void setGender(String gender){
        this.gender=gender;
    }
    public void setIncome(double income){
        this.income=income; 
    }
    public String getIncome(){
        return "£"+this.income;
    }
    public double getDoubleIncome(){
        return this.income;
    }
}
