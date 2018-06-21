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
public class Telephone {
    private String type;
    private String number;
    
    public Telephone(){
        
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
