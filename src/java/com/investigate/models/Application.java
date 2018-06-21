package com.investigate.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Application {

    private String reference,recommendation, investigationStatus, fraudStatus, decision;
    private int appNo, cusNo, riskScore;
    private LocalDate creationDate, applicationDate;
    private double value;
    
    private Product product;
    private Person mainApp;
    private Address currentAddress;
    private Bank bank;
    private Contact telephone;
    
    public Application(){}
    
    public Application(String reference, LocalDate applicationDate) {
        this.reference = reference;
        this.applicationDate = applicationDate;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getInvestigationStatus() {
        return investigationStatus;
    }

    public void setInvestigationStatus(String investigationStatus) {
        this.investigationStatus = investigationStatus;
    }

    public String getFraudStatus() {
        return fraudStatus;
    }

    public void setFraudStatus(String fraudStatus) {
        this.fraudStatus = fraudStatus;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public int getAppNo() {
        return appNo;
    }

    public void setAppNo(int appNo) {
        this.appNo = appNo;
    }

    public int getCusNo() {
        return cusNo;
    }

    public void setCusNo(int cusNo) {
        this.cusNo = cusNo;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }
    public String getApplicationDateString() {
        return applicationDate.format(DateTimeFormatter.ofPattern("dd-MM-YYYY"));
    }
    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public void setApplicationDate(String applicationDate) {
        this.setApplicationDate(LocalDate.parse(applicationDate));
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Person getMainApp() {
        return mainApp;
    }

    public void setMainApp(Person mainApp) {
        this.mainApp = mainApp;
    }

    public Address getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(Address currentAddress) {
        this.currentAddress = currentAddress;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Contact getTelephone() {
        return telephone;
    }

    public void setTelephone(Contact telephone) {
        this.telephone = telephone;
    }
}
