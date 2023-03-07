package com.sg.flooringorders.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Order {

    private String orderDateString;
    private String customerName;
    private String state;
    private String productType;
    private String area;

    private String stateAbbreviation;
    private String taxRate;
    private String costPerSquareFoot;
    private String laborCostPerSquareFoot;
    private String materialCost;
    private String laborCost;
    private String tax;
    private String total;

    //or maybe rename as order ID
    private String orderNum;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    LocalDate orderDate;



    public Order(String orderDateString){
        this.orderDateString = orderDateString;
    }

    public String getCustomerName(){
        return this.customerName;
    }

    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }

    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

    public String getProductType(){
        return this.productType;
    }

    public void setProductType(String productType){
        this.productType = productType;
    }

    public String getArea(){
        return this.area;
    }

    public void setArea(String area){
        this.area = area;
    }

    public String getOrderDate(){

        return orderDateString;
    }

    public void setOrderDate(String orderDateString){
        this.orderDateString = orderDateString;
    }

    public void setLaborCostPerSquareFoot(String laborCostPerSquareFoot) {
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public void setCostPerSquareFoot(String costPerSquareFoot) {
        this.costPerSquareFoot = costPerSquareFoot;
    }

    public String getCostPerSquareFoot() {
        return costPerSquareFoot;
    }

    public String getLaborCost() {
        return laborCost;
    }

    public String getLaborCostPerSquareFoot() {
        return laborCostPerSquareFoot;
    }

    public String getMaterialCost() {
        return materialCost;
    }

    public String getStateAbbreviation() {
        return stateAbbreviation;
    }

    public void setMaterialCost(String materialCost) {
        this.materialCost = materialCost;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public String getTotal() {
        return total;
    }

    public String getTax() {
        return tax;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setLaborCost(String laborCost) {
        this.laborCost = laborCost;
    }

    public void setStateAbbreviation(String stateAbbreviation) {
        this.stateAbbreviation = stateAbbreviation;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getOrderNumber() {
        return orderNum;
    }

    public void setOrderNumber(String orderNum) {
        this.orderNum = orderNum;
    }
}
