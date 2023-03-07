package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Order;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface FlooringOrderDao {

    List<Order> getOrdersForDate(String orderFile) throws FileNotFoundException;

    List<Integer> getOrderNumsForDate(String orderFile) throws FileNotFoundException;

    Order addOrderToFile(String orderFile, int orderNumber, Order order) throws IOException;

    Order addOrderToNewFile(String orderFile, int orderNumber, Order order) throws IOException;

    Order getOrder(String orderFile, int orderNum) throws FileNotFoundException;

    List<Integer> getAllOrderNums() throws FileNotFoundException;

    String[] listAllOrderFiles();
    Order editOrder(String orderFile, Order updatedOrder) throws IOException;

    Order deleteOrder(String orderFile, int orderNumber) throws IOException;
}
