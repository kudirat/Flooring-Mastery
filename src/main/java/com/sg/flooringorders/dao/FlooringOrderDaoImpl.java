package com.sg.flooringorders.dao;

import com.sg.flooringorders.dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FlooringOrderDaoImpl implements FlooringOrderDao{
    private Map<Integer, Order> orders = new HashMap<Integer, Order>();
    private final String DELIMITER = ",";
    private final String ORDER_FOLDER;

    public FlooringOrderDaoImpl() {
        this.ORDER_FOLDER = "src\\main\\Orders";
    }

    public FlooringOrderDaoImpl(String orderFolder) {
        this.ORDER_FOLDER = orderFolder;
    }

    public Order addOrderToNewFile(String fileName, int orderNumber, Order order) throws IOException {
        Order newOrder = orders.put(orderNumber, order);
        writeOrders(fileName);
        orders.clear();
        return newOrder;
    }

    public Order addOrderToFile(String fileName, int orderNumber, Order order) throws IOException {
        loadOrders(fileName);
        Order newOrder = orders.put(orderNumber, order);
        writeOrders(fileName);
        orders.clear();
        return newOrder;
    }

    public Order getOrder (String fileName, int orderNum) throws FileNotFoundException {
        loadOrders(fileName);
        Order orderToGet = orders.get(orderNum);
        orders.clear();
        return orderToGet;
    }

    public List<Order> getOrdersForDate(String fileName) throws FileNotFoundException {
        loadOrders(fileName);
        List<Order> allOrdersForADate = new ArrayList<Order>(orders.values());
        orders.clear();
        return allOrdersForADate;
    }

    public List<Integer> getOrderNumsForDate(String fileName) throws FileNotFoundException {
        loadOrders(fileName);
        List<Integer> orderNums = new ArrayList<Integer>(orders.keySet());
        orders.clear();
        return orderNums;
    }

    public List<Integer> getAllOrderNums() throws FileNotFoundException {
        String [] allOrderFiles = listAllOrderFiles();
        List<Integer> allOrderNums = new ArrayList<Integer>();

        if(allOrderNums.size() == 0 || allOrderNums == null){
            return null; //this could be handled better
        }

        for (String orderFile : allOrderFiles) {
            List<Integer> orderNumsForADate = getOrderNumsForDate(orderFile);
            orderNumsForADate.forEach(orderNum -> {
                allOrderNums.add(orderNum);
            });
        }
        orders.clear();
        return allOrderNums;
    }

    public String[] listAllOrderFiles() {
        FilenameFilter filter = (file, fileName) -> {
            return fileName.contains(".");
        };

        String [] orderFiles = new File(ORDER_FOLDER).list(filter);
        return orderFiles;
    }

    public Order editOrder(String fileName, Order updatedOrder) throws IOException {
        loadOrders(fileName);

        Order orderToEdit = orders.get(updatedOrder.getOrderNumber());

        orderToEdit.setCustomerName(updatedOrder.getCustomerName());
        orderToEdit.setStateAbbreviation(updatedOrder.getStateAbbreviation());
        orderToEdit.setTaxRate(updatedOrder.getTaxRate());
        orderToEdit.setProductType(updatedOrder.getProductType());
        orderToEdit.setArea(updatedOrder.getArea());
        orderToEdit.setCostPerSquareFoot(updatedOrder.getCostPerSquareFoot());
        orderToEdit.setLaborCostPerSquareFoot(updatedOrder.getLaborCostPerSquareFoot());
        orderToEdit.setMaterialCost(updatedOrder.getMaterialCost());
        orderToEdit.setLaborCost(updatedOrder.getLaborCost());
        orderToEdit.setTax(updatedOrder.getTax());
        orderToEdit.setTotal(updatedOrder.getTotal());

        writeOrders(fileName);
        return orderToEdit;
    }

    public Order deleteOrder(String fileName, int orderNumber) throws IOException {
        loadOrders(fileName);
        Order orderToRemove = orders.remove(orderNumber);
        writeOrders(fileName);
        return orderToRemove;
    }

    public void deleteOrderFile(String fileName) throws IOException {
        Path pathOfFile = Paths.get(this.ORDER_FOLDER + "\\" + fileName);
        Files.deleteIfExists(pathOfFile);
    }

    private void loadOrders(String fileName) throws FileNotFoundException{
        Scanner scanner = new Scanner(new BufferedReader(new FileReader(this.ORDER_FOLDER + "\\" + fileName)));

        String currentLine;
        Order currentOrder;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();

            //ignore the first line
            if (currentLine.startsWith("OrderNumber")) {
                continue;
            }
            currentOrder = unmarshallOrder(currentLine);
            int orderNum = Integer.parseInt(currentOrder.getOrderNumber());
            orders.put(orderNum, currentOrder);
        }
        scanner.close();
    }

    private void writeOrders(String fileName) throws IOException {
        PrintWriter out = new PrintWriter(new FileWriter(this.ORDER_FOLDER + "\\" + fileName));

        //Print out the first line in the file
        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

        String orderAsText;
        List<Order> orderList = new ArrayList<>(orders.values());
        for (Order currentOrder : orderList) {
            orderAsText = marshallOrder(currentOrder);
            out.println(orderAsText);
            out.flush();
        }
        out.close(); //close the printwriter
    }

    private String marshallOrder(Order order) {
        String orderAsString = order.getOrderNumber() + DELIMITER;
        orderAsString += order.getCustomerName() + DELIMITER;
        orderAsString += order.getStateAbbreviation() + DELIMITER;
        orderAsString += order.getTaxRate() + DELIMITER;
        orderAsString += order.getProductType() + DELIMITER;
        orderAsString += order.getArea() + DELIMITER;
        orderAsString += order.getCostPerSquareFoot() + DELIMITER;
        orderAsString += order.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsString += order.getMaterialCost() + DELIMITER;
        orderAsString += order.getLaborCost() + DELIMITER;
        orderAsString += order.getTax() + DELIMITER;
        orderAsString += order.getTotal();

        return orderAsString;
    }

    private Order unmarshallOrder(String currentLine) {
        String [] orderParts = currentLine.split(DELIMITER);

        Order fileOrder = new Order(orderParts[1]);

        fileOrder.setOrderNumber((orderParts[0]));
        fileOrder.setCustomerName(orderParts[1]);
        fileOrder.setStateAbbreviation(orderParts[2]);

        fileOrder.setTaxRate(orderParts[3]);
        fileOrder.setProductType(orderParts[4]);
        fileOrder.setArea(orderParts[5]);
        fileOrder.setCostPerSquareFoot(orderParts[6]);
        fileOrder.setLaborCostPerSquareFoot(orderParts[7]);
        fileOrder.setMaterialCost(orderParts[8]);
        fileOrder.setLaborCost(orderParts[9]);
        fileOrder.setTax(orderParts[10]);
        fileOrder.setTotal(orderParts[11]);

        return fileOrder;
    }


    private String getDateFromFileName(String fileName) {

        String date = fileName.substring(0, 2) + "/" + fileName.substring(2, 4) + "/" + fileName.substring(4);

//        //File name pattern "Order_MMDDYY.txt"
//        String [] fileNameTokens = fileName.split("_");
//
//        String [] dateTokens = fileNameTokens[1].split("\\.");
//
//        String date = dateTokens[0];
//
//        String mm = date.substring(0, 2);
//        String dd = date.substring(2, 4);
//        String yyyy = date.substring(4, 8);
//        //req format is MM-DD-YYYY
//        return mm+"-"+dd+"-"+yyyy;
        return date;
    }

    public String generateOrderNum(){
        int capacity = orders.size();
        capacity++;
        return Integer.toString(capacity);
    }

    //not needed :(
    public BigDecimal convertToBigDecimal(String valueStr){
        BigDecimal newValue = new BigDecimal(valueStr);
        return newValue;
    }

}
