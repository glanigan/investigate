/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.models;

import java.util.List;

/**
 *
 * @author garyl
 */
public class Rule {
    private int rulNo;
    private String type;
    private String name;
    private String description;
    private String decision;
    private int score;
    private boolean enabled;
    private List<Condition> conditions;
    
    public Rule(){}
    
    public Rule(int rulNo,String type,String name,String description,String decision,int score){
        this.rulNo=rulNo;
        this.type=type;
        this.name=name;
        this.description=description;
        this.decision=decision;
        this.score=score;
    }
    public void setRuleNo(int rulNo){
        this.rulNo=rulNo;
    }
    public int getRuleNo(){
        return this.rulNo;
    }
    public void setType(String type){
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDecision(String decision){
        this.decision=decision;
    }
    public String getDecision(){
        return this.decision;
    }
    public void setScore(int score){
        this.score=score;
    }
    public int getScore(){
        return this.score;
    }
    public String getEnabledString(){
        if(this.enabled!=true){
            return "Y";
        }
        else{
            return "N";
        }
    }
    public boolean getEnabled(){
        return this.enabled;
    }
    public void setEnabled(boolean enabled){
        this.enabled=enabled;
    }
    public void setEnabled(char enabled){
        if(enabled!='Y'){
            this.enabled=false;
        }else{
            this.enabled=true;
        }
    }
    public List<Condition> getConditions(){
        return conditions;
    }
    public void setConditions(List<Condition> conditions){
        this.conditions=conditions;
    }
    public void addCondition(Condition condition){
        this.conditions.add(condition);
    }
    public void removeCondition(){
        //TODO
    }
}
