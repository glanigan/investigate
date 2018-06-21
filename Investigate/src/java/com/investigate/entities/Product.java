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
public class Product {
    private int productNo;
    private String name;
    private String description;
    
    public Product(int productNo,String name,String description){
        this.productNo=productNo;
        this.name=name;
        this.description=description;
    }
    public int getProductNo(){
        return this.productNo;
    }
    public String getName(){
        return this.name;
    }
    public String getDecription(){
        return this.description;
    }
}
