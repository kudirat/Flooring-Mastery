package com.sg.flooringorders.ui;

import com.sg.flooringorders.dto.Order;
import com.sg.flooringorders.dto.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

public class FlooringMasteryView {


    private UserIO io = new UserIOConsoleImpl();

    public FlooringMasteryView(UserIO io){
        this.io = io;
    }
    public int printMenuSelection() {
        io.print("*  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        io.print("* <<Flooring Program>>");
        io.print("* 1. Display Orders");
        io.print("* 2. Add an Order");
        io.print("* 3. Edit an Order");
        io.print("* 4. Remove an Order");
        io.print("* 5. Export All Data");
        io.print("* 6. Exit");
        io.print("*\n" +
                "  * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

        return io.readInt("Please select from the above choices.", 1, 6);
    }


    public String getOrderDateListOrders(){
        return io.readString("Enter order date (YYYY-MM-DD): "); //helps grab orders for this date
    }

    public String getOrderDateCreateOrder() {
        return io.readString("Enter order date (YYYY-MM-DD): ");
    }

    public String getCustomerName() {
        return io.readString("Enter Customer Name: ");
    }

    public String displayOrderList(List<Order> orderList) {
        for (Order currentOrder : orderList) {

            String orderInfo = String.format("%s  %s  %s  %s  %s  %s  %s  %s  %s  %s  %s",
                    currentOrder.getCustomerName(),
                    currentOrder.getStateAbbreviation(),
                    currentOrder.getProductType(),
                    currentOrder.getTaxRate().toString(),
                    currentOrder.getArea().toString(),
                    currentOrder.getCostPerSquareFoot().toString(),
                    currentOrder.getLaborCostPerSquareFoot().toString(),
                    currentOrder.getMaterialCost().toString(),
                    currentOrder.getLaborCost().toString(),
                    currentOrder.getTax().toString(),
                    currentOrder.getTotal().toString());
            io.print(orderInfo);
        }
        return io.readString("Please hit enter to continue");
    }

    public String getProductInfo(){
        return io.readString("Pick a product type from the list above: ");
    }

    public String displayCurrProducts(Collection<Product> productList){
        io.print("Product type | CostPerSquareFoot | LaborCostPerSquareFoot ");
        productList.stream().map(currentProduct -> String.format("%s  |  %s  |  %s",
                currentProduct.getProductType(),
                currentProduct.getCostPerSquareFoot().toString(),
                currentProduct.getLaborCostPerSquareFoot().toString())).forEachOrdered(productInfo -> {
            io.print((String) productInfo);
        });
       String currProductType = getProductInfo();
       return currProductType;
    }

    public String getArea() {
        return io.readString("Please enter the area (Must be at least 100 square feet): ");
    }

    public String getStateAbbreviation() {
        return io.readString("Enter the state (abbreviated)");
    }

    public String getProductType() {
        return io.readString("What product type would you like?");
    }

    public void displayCreateSuccessHeader(String verifyOrder){
        if (verifyOrder.equalsIgnoreCase("Y")) {
            io.print("=== Order successfully created ===");
        } else {
            io.readString("Please press enter to continue.");
        }
    }

    public String displayNewOrderSummary(LocalDate orderDateInput, int orderNumber,String customerNameInput, String stateAbbreviationInput, String productTypeInput, BigDecimal areaInput, BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total){
        io.print("Order date:         " + orderDateInput);
        io.print("Customer Name:      " + customerNameInput);
        io.print("State Abbreviation: " + stateAbbreviationInput);
        io.print("Product:            " + productTypeInput);
        io.print("Area required:      " + areaInput);
        io.print("Material cost:      "+ materialCost);
        io.print("Labor cost:         "+ laborCost);
        io.print("Tax:                " + tax);
        io.print("Total:              " + total);

        return io.readString("Do you want to place the order? (Y/N).");
    }

    public void displayEditedOrderSummary(LocalDate orderDateInput,Order editedOrder) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        io.print("Order date:          " + orderDateInput.format(formatter));
        io.print("Customer Name:       " + editedOrder.getCustomerName());
        io.print("State abbreviation:  " + editedOrder.getStateAbbreviation());
        io.print("Product:             " + editedOrder.getProductType());
        io.print("Area required  :     " +editedOrder.getArea().toString());
        io.print("Material cost:       "+ editedOrder.getMaterialCost().toString());
        io.print("Labor cost:          "+ editedOrder.getLaborCost());
        io.print("Tax:                 " + editedOrder.getTax().toString());
        io.print("Total:               " + editedOrder.getTotal().toString());
    }

    public void displayOrderInformation(LocalDate orderDateInput, Order orderToRemove) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-DD");
        io.print("Order date:         " + orderDateInput.format(formatter));
        io.print("Customer Name:      " + orderToRemove.getCustomerName());
        io.print("State Abbreviation: " + orderToRemove.getStateAbbreviation());
        io.print("Product:            " + orderToRemove.getProductType());
        io.print("Area required:      " +orderToRemove.getArea().toString());
        io.print("Material cost:      " + orderToRemove.getMaterialCost().toString());
        io.print("Labor cost:         " + orderToRemove.getLaborCost());
        io.print("Tax:                " + orderToRemove.getTax().toString());
        io.print("Total:              " + orderToRemove.getTotal().toString());
    }

    public String getOrderDateEditOrder() {
        return io.readString("Enter a date (YYYY-MM-DD): ");
    }

    public String displayAndGetEditState (Order currOrder){
        return io.readString("Enter the state name (" + currOrder.getStateAbbreviation()+ "):");
    }

    public String displayAndGetEditProductType(Order currOrder){
        return io.readString("Enter product type (" + currOrder.getProductType() + "):");
    }

    public String displayAndGetEditArea(Order currOrder){
        return io.readString("Enter the area (" + currOrder.getArea()+"):");
    }

    public int getOrderNumberEditOrder(){
        return io.readInt("Enter the order number of the order you want to edit");
    }

    public String displayAndGetEditCustomerName(Order currOrder){
        return io.readString("Enter customer name (" + currOrder.getCustomerName() + "):");
    }


    public String getOrderDateRemoveOrder() {
        return io.readString("Enter a date (YYYY-MM-DD): ");
    }

    public int getOrderNumberRemoveOrder(){
        return io.readInt("Please enter the order number of the item you wish to remove: ");
    }

    public String getSaveConfirmation(){
        return io.readString("Do you want to save your changes? (Y/N)");
    }

    public String getRemoveConfirmation() {
        return io.readString("Do you want to remove this order? (Y/N)");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ERROR ===");
        io.print(errorMsg);
    }

    public void displayExitBanner() {
        io.print("Good bye!!!");
    }

    public void displayRemoveOrderBanner() {
        io.print("=== Remove Order ===");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown command!");
    }

    public void displayEditOrderBanner(){
        io.print("=== Edit Order ===");
    }

    public void displayCreateOrderBanner() {
        io.print("=== Add Order ===");
    }

    public void displayOrderListBanner(LocalDate orderDate) {
        io.print("========= All Orders for " + orderDate + " =========");
    }

    public void displayListOrdersBanner() {
        io.print("=== List Orders ===");
    }

    public void displayRemoveSuccessBanner(Order removedOrder){
        if (removedOrder == null) {
            io.readString("Please hit enter to continue to main menu.");
        } else {
            io.print("=== Order Removed Successfully ===");
        }
    }
}
