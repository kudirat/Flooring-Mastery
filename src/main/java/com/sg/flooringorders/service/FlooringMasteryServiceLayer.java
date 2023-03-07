package com.sg.flooringorders.service;

import com.sg.flooringorders.dto.Order;
import com.sg.flooringorders.dto.Product;
import com.sg.flooringorders.dto.Tax;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface FlooringMasteryServiceLayer {

        void checkOrderFileExists (String orderfileName) throws FlooringMasteryServiceException;

        String createOrderFileName(LocalDate date);

        List<Order> getAllOrders(String fileWithDate) throws FileNotFoundException;

        List<Order> getOrderList(LocalDate wantedOrderDate) throws FlooringMasteryServiceException, FileNotFoundException;


        LocalDate checkDateIsInFuture(LocalDate orderDate) throws FlooringMasteryServiceException;

;
        void validateCustomerName(String customerNameInput) throws FlooringMasteryServiceException;

         void checkState(String stateAbbreviationInput) throws FlooringMasteryServiceException, FileNotFoundException;

        List<Product> getAllProducts() throws FileNotFoundException;

        void checkProductType(String productTypeInput) throws FlooringMasteryServiceException, FileNotFoundException;

        Product getProduct(String productType) throws FileNotFoundException;

        void checkArea(BigDecimal areaInput) throws FlooringMasteryServiceException;

        BigDecimal calcMaterialCost(BigDecimal area, BigDecimal costPerSquareFoot);

        BigDecimal calcTax(BigDecimal materialCost, BigDecimal laborCost,BigDecimal taxRate);

        BigDecimal calcTotal(BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax);

        BigDecimal calcLaborCost(BigDecimal area,BigDecimal laborCostPerSquareFoot);

        Tax getTax(String stateAbbreviationInput) throws FileNotFoundException;

        int generateNewOrderNum() throws FileNotFoundException;

        Order createNewOrder(String verifyOrder, LocalDate orderDateInput, int orderNumber, String customerNameInput, String stateAbbreviationInput, BigDecimal taxRate, String productTypeInput, BigDecimal areaInput, BigDecimal CostPerSquareFoot, BigDecimal laborCostPerSquareFoot, BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total) throws IOException;

        String checkEdit(String updatedInfo);


        BigDecimal checkEditBigDecimal (String updatedInfo);

        Order updateOrderCustomerName(String updatedCustomerName, Order orderToEdit);

        Order updateOrderState(String updatedState, Order orderToEdit);

        Order updateOrderProductType(String updatedProductType, Order orderToEdit);

        Order updateOrderArea(BigDecimal updatedArea, Order orderToEdit);

        Order recalculateOrder(Order editedOrder) throws FileNotFoundException;

        Order editOrder(String toBeEdited, String orderFile, Order updatedOrder) throws IOException;

        Order removeOrderIfConfirmed(String removeConfirmation, String orderFile, int orderNumber) throws IOException;

        int checkOrderNumExists(String orderFileName, int orderNumberInput) throws FlooringMasteryServiceException, FileNotFoundException;

        Order getOrder(String fileName, int orderNum) throws FileNotFoundException;

}
