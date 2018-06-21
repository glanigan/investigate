/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.models;

/**
 *
 * @author garyl
 */
public class Contact {
    private String type, number, email;
    
    public Contact(){
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getType(){
        return this.type;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getNumber(){
        return this.number;
    }
    public void setNumber(String number){
        this.number=number;
    }
    public boolean isNull(){
        System.out.println(type+number);
        return this.type==null && this.number==null;
    }
}
