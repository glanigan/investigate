/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.models;

import com.investigate.controllers.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class Person {
    private enum Gender{
        MALE,FEMALE,OTHER,UNKNOWN
    }
    private String title;
    private String firstname;
    private String middlename;
    private String surname;
    private LocalDate dob;
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
    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }
    public String getSurname(){
        return this.surname;
    }
    public void setSurname(String surname){
        this.surname=surname;
    }
    public LocalDate getDob(){
        return this.dob;
    }
    public String getStringDob(){
            return this.dob.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
    }
    public void setDob(String dob){
            this.dob=LocalDate.parse(dob);
    }
    public void setDob(LocalDate dob){
        this.dob=dob;
    }
    public String getFullname(){
        return StringConverter.toTitleCase(firstname+" "+surname);
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
