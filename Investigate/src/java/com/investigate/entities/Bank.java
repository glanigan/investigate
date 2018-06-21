/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.entities;

/**
 *
 * @author garyl
 */
public class Bank {
    private String sortCode;
    private String accountNumber;
    public Bank(){
        
    }
    public String getSortCode(){
        return this.sortCode;
    }
    public void setSortCode(String sortCode){
        this.sortCode=sortCode;
    }
    public String getAccountNumber(){
        return this.accountNumber;
    }
    public void setAccountNumber(String accountNumber){
        this.accountNumber=accountNumber;
    }
    public boolean isNull(){
        System.out.println(sortCode+accountNumber);
        return this.sortCode==null && this.accountNumber==null;
    }
}
