package com.sg.flooringorders.dto;

import java.math.BigDecimal;

public class Tax {
    private String stateAbbreviation;
    private String stateName;
    private BigDecimal taxRate;

    public Tax(String stateAbbreviation, String stateName, BigDecimal taxRate){
        this.stateAbbreviation = stateAbbreviation;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbreviation(){
        return stateAbbreviation;
    }

    public String getStateName(){
        return stateName;
    }

    public BigDecimal getTaxRate(){
        return taxRate;
    }

    public void setStateAbbreviation(String stateAbbreviation){
        this.stateAbbreviation = stateAbbreviation;
    }

    public void setStateName(String stateName){
        this.stateName = stateName;
    }

    public void setTaxRate(BigDecimal taxRate){
        this.taxRate = taxRate;
    }
}
