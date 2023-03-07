package com.sg.flooringorders.service;

import com.sg.flooringorders.dao.FlooringOrderDao;
import com.sg.flooringorders.dao.ProductFileDao;
import com.sg.flooringorders.dao.TaxFileDao;
import com.sg.flooringorders.dto.Order;
import com.sg.flooringorders.dto.Product;
import com.sg.flooringorders.dto.Tax;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {
    private FlooringOrderDao orderDao;
    private ProductFileDao productDao;
    private TaxFileDao taxDao;

    public final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MMddyyyy"); //Convert YYYY-MM-DD to MMDDYYYY


    public FlooringMasteryServiceLayerImpl(FlooringOrderDao orderDao, ProductFileDao productDao, TaxFileDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }


    public String createOrderFileName(LocalDate date) {
        String dateFormatted = date.format(FORMATTER);
        String fileName = "Orders_" + dateFormatted + ".txt";
        return fileName;
    }

    public void checkOrderFileExists(String orderFileName) throws FlooringMasteryServiceException {
        String[] currOrderFiles = orderDao.listAllOrderFiles();
        String orderfile = null;
        for (String orderFile : currOrderFiles) {
            if (orderFileName.equals(orderFile)) {//compare the order file names to the file name given
                orderfile = orderFileName;
                if (orderfile != null) {//if it is found, set name to the orderfile name
                    break;
                }
            }
        }
        if (orderfile == null) {
            throw new FlooringMasteryServiceException(
                    "ERROR: no orders exist for that date.");
        }
    }

    public List<Order> getAllOrders(String file) throws FileNotFoundException {
        //Lists all the orders from the file specified.
        return orderDao.getOrdersForDate(file);
    }

    public List<Order> getOrderList(LocalDate orderDate) throws FlooringMasteryServiceException, FileNotFoundException {
        String currFile = createOrderFileName(orderDate);
        checkOrderFileExists(currFile);
        return getAllOrders(currFile);

    }


    public LocalDate checkDateIsInFuture(LocalDate orderDate) throws FlooringMasteryServiceException {
        LocalDate cuttOff = LocalDate.parse("2023-03-05");
        if (orderDate.isBefore(cuttOff)) {
            throw new FlooringMasteryServiceException(
                    "ERROR: Date must be in the future!");
        }
        return orderDate;
    }

    public void validateCustomerName(String customerNameInput) throws FlooringMasteryServiceException {
        if (customerNameInput.length() <= 0 && (customerNameInput.matches("[A-Za-z0-9,. ]+"))) {
            throw new FlooringMasteryServiceException(
                    "ERROR: You did not enter a valid Customer Name!");
        }
    }

    public void checkState(String stateAbbrevParam) throws FlooringMasteryServiceException, FileNotFoundException {
        List<Tax> taxes = taxDao.getAllTaxes();
        String stateAbbrev = null;
        for (Tax tax : taxes) {
            if (tax.getStateAbbreviation().equalsIgnoreCase(stateAbbrevParam)) {
                stateAbbrev = tax.getStateAbbreviation();
                if (stateAbbrev != null) {
                    break;
                }
            }
        }
        if (stateAbbrev == null) {
            throw new FlooringMasteryServiceException(
                    "ERROR: Please enter a valid state.");
        }
    }
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public void checkProductType(String productTypeParam) throws FlooringMasteryServiceException, FileNotFoundException {
        List<Product> productList = productDao.getAllProducts();
        String productType = null;

        for (Product product : productList) {
            if (product.getProductType().equalsIgnoreCase(productTypeParam)) {
                productType = product.getProductType();
            }
        }
        if (productType == null) {
            throw new FlooringMasteryServiceException(
                    "ERROR: " + productTypeParam + " is currently not in the product list.");
        }
    }

    public Product getProduct(String productType) {
        return productDao.getProduct(productType);
    }
    public void checkArea(BigDecimal area) throws FlooringMasteryServiceException {
        if (area.compareTo(new BigDecimal("100")) < 0) {
            throw new FlooringMasteryServiceException(
                    "ERROR: the area is below the minimum order!");
        }
    }

    public BigDecimal calcMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot) {
        return area.multiply(costPerSquareFoot);
    }

    public BigDecimal calcLaborCost(BigDecimal area, BigDecimal laborCostPerSquareFoot) {
        return area.multiply(laborCostPerSquareFoot);
    }

    public BigDecimal calcTax(BigDecimal materialCost, BigDecimal laborCost, BigDecimal taxRate) {
        return (materialCost.add(laborCost)).multiply(taxRate.divide(new BigDecimal("100")));
    }

    public BigDecimal calcTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax) {
        return materialCost.add(laborCost).add(tax);
    }
    public Tax getTax(String stateAbbreviationInput) throws FileNotFoundException {
        return taxDao.getTax(stateAbbreviationInput);
    }

    @Override
    public int generateNewOrderNum() throws FileNotFoundException {
        List<Integer> currNums = this.orderDao.getAllOrderNums();
        if(currNums == null){
            return 1;
        }
        int currMaxNum = currNums.size();
        return currMaxNum + 1;
    }

    public Order createNewOrder(String userInput, LocalDate orderDate, int orderNum, String customerName, String state, BigDecimal taxRate, String productType,
                                BigDecimal area, BigDecimal CostPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total)
            throws IOException {
        Order newOrder;
        if (userInput.equalsIgnoreCase("Y")) {
            newOrder = new Order(customerName);
            newOrder.setOrderNumber(String.valueOf(orderNum));
            newOrder.setCustomerName(customerName);
            newOrder.setStateAbbreviation(state);
            newOrder.setTaxRate(String.valueOf(taxRate));
            newOrder.setProductType(productType);
            newOrder.setArea(String.valueOf(area));
            newOrder.setCostPerSquareFoot(String.valueOf(CostPerSquareFoot));
            newOrder.setLaborCostPerSquareFoot(String.valueOf(laborCostPerSquareFoot));
            newOrder.setMaterialCost(String.valueOf(materialCost));
            newOrder.setLaborCost(String.valueOf(laborCost));
            newOrder.setTax(String.valueOf(tax));
            newOrder.setTotal(String.valueOf(total));

            String newOrderFileName = createOrderFileName(orderDate);
            String[] currOrderFiles = orderDao.listAllOrderFiles();
            String currFile = null;

            for (String currOrderFile : currOrderFiles) {
                if (newOrderFileName.equals(currOrderFile)) {
                    currFile = newOrderFileName;
                    Order orderCreated = orderDao.addOrderToFile(currFile, orderNum, newOrder);
                    return orderCreated;
                }
            }

            if (currFile == null) {
                Order orderCreated = orderDao.addOrderToNewFile(newOrderFileName, orderNum, newOrder);
                return orderCreated;
            }
        }
        return null;
    }

     public String checkEdit(String updatedInfo) {
        if (updatedInfo == null || updatedInfo.trim().length() <= 0) {
            return null;
        } else {
            return updatedInfo;
        }
    }

    public BigDecimal checkEditBigDecimal(String updatedInfo) {
        if (updatedInfo == null || updatedInfo.trim().length() <= 0) {
            return null;
        } else {
            return new BigDecimal(updatedInfo);
        }
    }

    public Order updateOrderCustomerName(String customerName, Order order) {
        if (customerName != null) {
            order.setCustomerName(customerName);
        }
        return order;
    }

    public Order updateOrderState(String state, Order order) {
        if (state != null) {
            order.setStateAbbreviation(state);
        }
        return order;
    }

    public Order updateOrderProductType(String productType, Order order) {
        if (productType != null) {
            order.setProductType(productType);
        }
        return order;
    }

    public Order updateOrderArea(BigDecimal area, Order order) {
        if (area != null) {
            order.setArea(String.valueOf(area));
        }
        return order;
    }

    public Order recalculateOrder(Order order) {
        BigDecimal updatedTaxRate = null;
        BigDecimal updatedCostPerSquareFoot = null;
        BigDecimal updatedLaborCostPerSquareFoot = null;

        String updatedStateAbbreviation = order.getStateAbbreviation();
        if (updatedStateAbbreviation != null) {
            Tax updatedTaxObj = taxDao.getTax(updatedStateAbbreviation);
            updatedTaxRate = updatedTaxObj.getTaxRate();
        }

        String updatedProductType = order.getProductType();
        if (updatedProductType != null) {

            Product updatedProduct = productDao.getProduct(updatedProductType);
            updatedCostPerSquareFoot = updatedProduct.getCostPerSquareFoot();
            updatedLaborCostPerSquareFoot = updatedProduct.getLaborCostPerSquareFoot();
            BigDecimal updatedArea = new BigDecimal(order.getArea());

            BigDecimal updatedMaterialCost = calcMaterialCost(updatedArea, updatedCostPerSquareFoot);
            BigDecimal updatedLaborCost = calcLaborCost(updatedArea, updatedLaborCostPerSquareFoot);
            BigDecimal updatedTax = calcTax(updatedMaterialCost, updatedLaborCost, updatedTaxRate);
            BigDecimal updatedTotal = calcTotal(updatedMaterialCost, updatedLaborCost, updatedTax);
            order.setMaterialCost(String.valueOf(updatedMaterialCost));
            order.setLaborCost(String.valueOf(updatedLaborCost));
            order.setTax(String.valueOf(updatedTax));
            order.setTotal(String.valueOf(updatedTotal));
        }
        return order;
    }

    public Order editOrder(String userConfirm, String orderFile, Order order) throws IOException {
        if (userConfirm.equalsIgnoreCase("Y")) {
            Order updatedOrder = orderDao.editOrder(orderFile, order);
            return updatedOrder;
        }
        return null;
    }
   public Order removeOrderIfConfirmed(String userConfirm, String orderFile, int orderNum) throws IOException {
        if (userConfirm.equalsIgnoreCase("Y")) {
            Order order = orderDao.deleteOrder(orderFile, orderNum);
            return order;
        }
        return null;
    }
    public int checkOrderNumExists(String orderFileName, int orderNumberParam) throws FlooringMasteryServiceException, FileNotFoundException {
        List<Integer> orderNums = orderDao.getOrderNumsForDate(orderFileName);

        int orderNumFound = 0;
        for (Integer orderNum : orderNums) {
            if (orderNumberParam == orderNum) {
                orderNumFound = orderNum;
                return orderNumFound;
            }
        }
        return 0;
    }

    public Order getOrder(String fileName, int orderNum) throws FileNotFoundException {
        return orderDao.getOrder(fileName, orderNum);
    }
}
