/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.entities;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author garyl
 */
public class Application {
    public enum Decision{
        APPROVED,DECLINE,REFER
    }
    private int appNo;
    private Date creationDate;
    private int cusNo;
    private String product;
    private String reference;
    private Date applicationDate;
    private int value;
    
    private Decision decision;
    private String recommendation;
    private String investigationStatus;
    private String fraudStatus;
    private int riskScore;
    
    private Person mainApp;
    private Address currentAddress;
    private Bank bank;
    private Telephone telephone;
    
    public Application(){}
    
    public Application(String reference, Date applicationDate){
        this.reference=reference;
        this.applicationDate=applicationDate;
    }
    public Integer getAppNo(){
        return this.appNo;
    }
    public void setAppNo(int appNo){
        this.appNo=appNo;
    }
    public String getReference(){
        return reference;
    }
    public void setReference(String reference){
        this.reference=reference;
    }
    public Date getApplicationDate(){
        return applicationDate;
    }
    public void setApplicationDate(Date applicationDate){
        this.applicationDate=applicationDate;
    }
    public void setApplicationDate(String applicationDate){
        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        try {
            this.applicationDate=formatter.parse(applicationDate);
        } catch (ParseException ex) {
            Logger.getLogger(Application.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getValue(){
        return value;
    }
    public void setValue(int value){
        this.value=value;
    }
    public int getRiskScore(){
        return riskScore;
    }
    public void setRiskScore(int riskScore){
        this.riskScore=riskScore;
    }
    public void setMainApp(Person person){
        this.mainApp=person;
    }
    public Person getMainApp(){
        return this.mainApp;
    }
    public void setCurrentAddress(Address currentAddress){
        this.currentAddress=currentAddress;
    }
    public Address getCurrentAddress(){
        return this.currentAddress;
    }
    public Bank getBank(){
        return this.bank;
    }
    public void setBank(Bank bank){
        this.bank=bank;
    }
    public Telephone getTelephone(){
        return this.telephone;
    }
    public void setTelephone(Telephone telephone){
        this.telephone=telephone;
    }
    public void setProduct(String product){
        this.product=product;
    }
    public String getProduct(){
        return this.product;
    }
    public String getFraudStatus(){
        return this.fraudStatus;
    }
    public void setFraudStatus(String fraudStatus){
        this.fraudStatus=fraudStatus;
    }
    public void setRecommendation(String recommendation){
        this.recommendation=recommendation;
    }
    public String getRecommendation(){
        return this.recommendation;
    }
}
