package com.sg.flooringorders.controller;

import com.sg.flooringorders.dto.Order;
import com.sg.flooringorders.dto.Product;
import com.sg.flooringorders.dto.Tax;
import com.sg.flooringorders.service.FlooringMasteryServiceException;
import com.sg.flooringorders.service.FlooringMasteryServiceLayer;
import com.sg.flooringorders.ui.FlooringMasteryView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FlooringMasteryController {
    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    /**
     * Our Runner Method! (Runs the Menu)
     */
    public void run() {
        boolean keepGoing = true;
        int menuSelection = 0;
        while (keepGoing) {
            menuSelection = view.printMenuSelection();
            switch (menuSelection) {
                case 1:
                    listOrders();
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    //exportAllData();
                    break;
                case 6:
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
            }
        }
        exitMessage();
    }

    private void listOrders() {
        view.displayListOrdersBanner();
        boolean hasErrors = false; //help with user input validation
        List<Order> orderList = null;
        do {
            try {
                String orderDateStr = view.getOrderDateListOrders(); //get the order date and parse it into a local date obj
                LocalDate orderDate = LocalDate.parse(orderDateStr);

                orderList = service.getOrderList(orderDate);
                view.displayOrderListBanner(orderDate);
                hasErrors = false;
            } catch (FileNotFoundException | FlooringMasteryServiceException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasErrors);
        view.displayOrderList(orderList);
    }

    private void addOrder() {
        view.displayCreateOrderBanner();
        boolean hasErrors = false;
        do {
            try {
                String orderDateStr = view.getOrderDateCreateOrder();
                LocalDate orderDateInput = LocalDate.parse(orderDateStr);
                service.checkDateIsInFuture(orderDateInput);

                String customerName = view.getCustomerName();
                service.validateCustomerName(customerName);

                String stateAbbreviation = view.getStateAbbreviation();
                service.checkState(stateAbbreviation);

                List<Product> availableProducts = new ArrayList<>(service.getAllProducts());
                String productType = view.displayCurrProducts(availableProducts);
                service.checkProductType(productType);
                Product productSelected = service.getProduct(productType);

                String areaStr = view.getArea();
                BigDecimal area = new BigDecimal(areaStr);
                service.checkArea(area);

                BigDecimal materialCost = service.calcMaterialCost(area, productSelected.getCostPerSquareFoot());

                BigDecimal laborCost = service.calcLaborCost(area, productSelected.getLaborCostPerSquareFoot());

                Tax taxObj = service.getTax(stateAbbreviation);
                BigDecimal tax = service.calcTax(materialCost, laborCost, taxObj.getTaxRate());

                BigDecimal total = service.calcTotal(materialCost, laborCost, tax);

                int orderNumber = service.generateNewOrderNum();

                String verifyOrder = view.displayNewOrderSummary(orderDateInput, orderNumber, customerName, stateAbbreviation,
                        productType, area, materialCost, laborCost, tax, total);

                service.createNewOrder(verifyOrder, orderDateInput, orderNumber, customerName, stateAbbreviation,
                        tax, productType, area, materialCost, laborCost, materialCost, laborCost, tax, total);

                view.displayCreateSuccessHeader(verifyOrder);

                hasErrors = false;
            } catch (FlooringMasteryServiceException | IOException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }

        } while(hasErrors);
    }

    private void editOrder() {
        view.displayEditOrderBanner();
        boolean hasErrors = false;
        do {
            String orderDateStr = view.getOrderDateEditOrder();
            LocalDate orderDateInput = LocalDate.parse(orderDateStr);

            int orderNumber = view.getOrderNumberEditOrder();
            try {
                String orderFileName = service.createOrderFileName(orderDateInput);
                service.checkOrderFileExists(orderFileName);

                int orderNumToEdit = service.checkOrderNumExists(orderFileName, orderNumber);
                Order orderToEdit = service.getOrder(orderFileName, orderNumToEdit);

                String newCustomerName = view.displayAndGetEditCustomerName(orderToEdit);
                newCustomerName = service.checkEdit(newCustomerName);

                Order updatedOrder = service.updateOrderCustomerName(newCustomerName, orderToEdit);

                String updatedStateAbbreviation = view.displayAndGetEditState(orderToEdit);
                updatedStateAbbreviation = service.checkEdit(updatedStateAbbreviation);
                if (updatedStateAbbreviation != null){
                    service.checkState(updatedStateAbbreviation);
                }
                updatedOrder = service.updateOrderState(updatedStateAbbreviation, orderToEdit);

                String newProductType = view.displayAndGetEditProductType(orderToEdit);
                newProductType = service.checkEdit(newProductType);
                if (newProductType != null) {
                    service.checkProductType(newProductType);
                }
                updatedOrder = service.updateOrderProductType(newProductType, orderToEdit);

                String updatedAreaString = view.displayAndGetEditArea(orderToEdit);
                BigDecimal updatedArea = service.checkEditBigDecimal(updatedAreaString);
                if (updatedArea!=null) {
                    service.checkArea(updatedArea);
                }
                updatedOrder = service.updateOrderArea(updatedArea, orderToEdit);

                updatedOrder = service.recalculateOrder(updatedOrder);

                view.displayEditedOrderSummary(orderDateInput,orderToEdit);

                String toBeEdited = view.getSaveConfirmation();

                Order editedOrder = service.editOrder(toBeEdited,orderFileName,updatedOrder);

                hasErrors = false;
            } catch (FlooringMasteryServiceException | IOException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while(hasErrors);

    }

    private void removeOrder() {
        view.displayRemoveOrderBanner();
        boolean hasErrors = false;
        do {
            //prompt user
            String orderDateStr = view.getOrderDateRemoveOrder();
            LocalDate orderDate = LocalDate.parse(orderDateStr);
            int orderNumber = view.getOrderNumberRemoveOrder();
            try {
                String orderFileName = service.createOrderFileName(orderDate);
                service.checkOrderFileExists(orderFileName);

                //if the order with this order number exists in the file, remove it
                int currNum = service.checkOrderNumExists(orderFileName, orderNumber);
                Order currOrder = service.getOrder(orderFileName, currNum);
                view.displayOrderInformation(orderDate, currOrder);

                //Prompt the user if they are sure they want to remove the order
                String userConfirm = view.getRemoveConfirmation();

                //If yes, remove. If removedOrder is null, they will just return to main menu
                Order removedOrder = service.removeOrderIfConfirmed(userConfirm, orderFileName, orderNumber);

                view.displayRemoveSuccessBanner(removedOrder);
                hasErrors = false;
            } catch (FlooringMasteryServiceException | IOException e) {
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }


}
