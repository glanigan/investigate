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
public class Configuration {
    private boolean rDecision,matchDecision;
    private int scoreDecision;
    
    public Configuration(boolean rDecision,boolean matchDecision,int scoreDecision){
        this.rDecision=rDecision;
        this.matchDecision=matchDecision;
        this.scoreDecision=scoreDecision;
    }

    public boolean isrDecision() {
        return rDecision;
    }

    public void setrDecision(boolean rDecision) {
        this.rDecision = rDecision;
    }

    public boolean isMatchDecision() {
        return matchDecision;
    }

    public void setMatchDecision(boolean matchDecision) {
        this.matchDecision = matchDecision;
    }

    public int getScoreDecisionInt() {
        return scoreDecision;
    }
    public boolean isScoreDecision(){
        return scoreDecision != 0;
    }
    public void setScoreDecision(int scoreDecision) {
        this.scoreDecision = scoreDecision;
    }
}
