/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.investigate.entities;


public class Match {
    
    private int lhsApplicationNo;
    private String lhsApplicationReference;
    private int rhsApplicationNo;
    private String rhsApplicationReference;
    private Rule rule;
    
    public Match(){
        
    }
    public Match(int lhsApplicationNo,int rhsApplicationNo,Rule rule){
       this.lhsApplicationNo=lhsApplicationNo;
        this.rhsApplicationNo=rhsApplicationNo;
        this.rule= rule;
    }
    public Match(int lhsApplicationNo,int rhsApplicationNo,int ruleNo,String ruleName){
        this.lhsApplicationNo=lhsApplicationNo;
        this.rhsApplicationNo=rhsApplicationNo;
        this.rule= new Rule();
        rule.setRuleNo(ruleNo);
        rule.setName(ruleName);
    }
    public int getLhsApplicationNo(){
        return this.lhsApplicationNo;
    }
    public int getRhsApplicationNo(){
        return this.rhsApplicationNo;
    }
    public String getReferenceRHS(){
        return this.rhsApplicationReference;
    }
    public final void setReferenceRHS(String referenceRHS){
        this.rhsApplicationReference=referenceRHS;
    }
    public void setRule(Rule rule){
        this.rule=rule;
    }
    public Rule getRule(){
        return this.rule;
    }
}
