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
public class Product {
    private int productNo;
    private String type,name,description;
    
    public Product(int productNo){
        this.productNo=productNo;
    }
    public Product(int productNo,String type,String name,String description){
        this.productNo=productNo;
        this.type=type;
        this.name=name;
        this.description=description;
    }
    public int getProductNo(){
        return this.productNo;
    }
    public String getType(){
        return this.type;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    
}
