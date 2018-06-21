package com.investigate.models;

public class Condition {

    public enum ConditionType {
        CORE, FILTER, GROUP, OTHER
    }
    private int conditionNo;
    private String name, description, query, parameter;
    private ConditionType type;
    
    public int getConditionNo(){
        return conditionNo;
    }
    
    public void setConditionNo(int conditionNo){
        this.conditionNo=conditionNo;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ConditionType getType() {
        return type;
    }

    public void setType(ConditionType type) {
        this.type = type;
    }

    public void setType(String type) {
        if (type != null) {
            switch (type) {
                case "C":
                    this.type = ConditionType.CORE;
                    break;
                case "F":
                    this.type = ConditionType.FILTER;
                    break;
                case "G":
                    this.type = ConditionType.GROUP;
                    break;
                default:
                    this.type = ConditionType.OTHER;
                    break;
            }
        }
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getParameter() {
        return this.parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }
}
