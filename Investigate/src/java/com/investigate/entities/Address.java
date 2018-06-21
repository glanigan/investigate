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
public class Address {
    
    private String buildingName;
    private String buildingNumber;
    private String street;
    private String postTown;
    private String postcode;
    
    public Address(){
        
    }
    public Address(String buildingName,String buildingNumber,String street,String postTown,String postcode){
        this.buildingName=buildingName;
        this.buildingNumber=buildingNumber;
        this.street=street;
        this.postTown=postTown;
        this.postcode=postcode;
    }
    public String getBuildingName(){
        return this.buildingName;
    }
    public void setBuildingName(String buildingName){
        this.buildingName=buildingName;
    }
    public String getBuildingNumber(){
        return this.buildingNumber;
    }
    public void setBuildingNumber(String buildingNumber){
        this.buildingNumber=buildingNumber;
    }
    public String getStreet(){
        return this.street;
    }
    public void setStreet(String street){
        this.street=street;
    }
    public String getPostTown(){
        return this.postTown;
    }
    public void setPostTown(String postTown){
        this.postTown=postTown;
    }
    public String getPostcode(){
        return this.postcode;
    }
    public void setPostcode(String postcode){
        this.postcode=postcode;
    }
    
}
