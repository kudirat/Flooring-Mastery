package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Order;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FlooringOrderStubImpl implements FlooringOrderDao{

    private final String TEST_FOLDER;
    private Map<Integer, Order> orderMap = new HashMap<>();
    public FlooringOrderStubImpl(){
        this.TEST_FOLDER = "src\\main\\testOrders";
        //Customer 1!
        String customerName = "Foo";
        Order order1 = new Order(customerName);
        order1.setOrderDate("2023-04-16");
        order1.setOrderNumber("1");
        order1.setState("California");
        order1.setStateAbbreviation("CA");
        order1.setProductType("Tile");
        order1.setTax("12.00");
        order1.setLaborCostPerSquareFoot("11.00");
        order1.setCostPerSquareFoot("5.00");
        order1.setLaborCost("15.00");
        order1.setMaterialCost("20.00");
        order1.setTaxRate("2.50");
        order1.setTotal("2000.00");


        //Customer 2!
        String customerName2 = "Bar";
        Order order2 = new Order(customerName2);
        order2.setOrderDate("2023-03-01");
        order1.setOrderNumber("2");
        order1.setState("California");
        order1.setStateAbbreviation("CA");
        order1.setProductType("Wood");
        order1.setTax("12.00");
        order1.setLaborCostPerSquareFoot("11.00");
        order1.setCostPerSquareFoot("5.00");
        order1.setLaborCost("15.00");
        order1.setMaterialCost("20.00");
        order1.setTaxRate("2.50");
        order1.setTotal("2000.00");

    }
    @Override
    public List<Order> getOrdersForDate(String orderFile) throws FileNotFoundException {
        System.out.println("Not Supported yet!");
        return null;
    }

    @Override
    public List<Integer> getOrderNumsForDate(String orderFile) throws FileNotFoundException {
        System.out.println("Not Supported yet!");
        return null;
    }

    @Override
    public Order addOrderToFile(String orderFile, int orderNumber, Order order) throws IOException {
        System.out.println("Not Supported yet!");
        return null;
    }

    @Override
    public Order addOrderToNewFile(String orderFile, int orderNumber, Order order) throws IOException {
        System.out.println("Not Supported yet!");
        return null;
    }

    @Override
    public Order getOrder(String orderFile, int orderNum) throws FileNotFoundException {
        System.out.println("Not Supported yet!");
        return null;
    }

    @Override
    public List<Order> getAllOrders() throws FileNotFoundException {
        System.out.println("Not Supported yet!");
        return null;
    }

    @Override
    public List<Integer> getAllOrderNums() throws FileNotFoundException {
        System.out.println("Not Supported yet!");
        return null;
    }

    @Override
    public String[] listAllOrderFiles() {
        System.out.println("Not Supported yet!");
        return new String[0];
    }

    @Override
    public Order editOrder(String orderFile, Order updatedOrder) throws IOException {
        System.out.println("Not Supported yet!");
        return null;
    }

    @Override
    public Order deleteOrder(String orderFile, int orderNumber) throws IOException {
        System.out.println("Not Supported yet!");
        return null;
    }

}
