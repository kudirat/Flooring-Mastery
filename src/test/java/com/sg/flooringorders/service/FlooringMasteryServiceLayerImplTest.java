package com.sg.flooringorders.service;

import com.sg.flooringorders.dao.*;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryServiceLayerImplTest {

    FlooringMasteryServiceLayer service;
    FlooringOrderDao orderDao = new FlooringOrderDaoImpl();

    public FlooringMasteryServiceLayerImplTest(){
        //these stubs currently do nothing right now
        ProductFileDao productDao = new ProductFileDaoStubImpl();
        TaxFileDao taxDao = new TaxFileDaoStubImpl();

        service = new FlooringMasteryServiceLayerImpl(orderDao, productDao, taxDao);
    }

    @BeforeAll
    public static void setUpClass() {

    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        String testFolder = "src\\main\\testOrders";
        orderDao = new FlooringOrderDaoImpl(testFolder);

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testCreateOrderFileName() {
        //create an order and check that it's file name is correct
        String expectedFileName = "Orders_03172023";
        LocalDate date = LocalDate.parse("2023-03-17");

        String result = service.createOrderFileName(date);
        assertFalse(result.equals(date));
    }

    @Test
    public void testCheckDateInFuture() throws FlooringMasteryServiceException {
        //checks to see if exception is thrown
        LocalDate date = LocalDate.parse("2023-03-01");

        //Baeldung was very useful in finding this resource! And lambda!
        Exception exception = assertThrows(FlooringMasteryServiceException.class, () -> {
            LocalDate result = service.checkDateIsInFuture(date);
        });

        String expectedMessage = "ERROR: Date must be in the future!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void validCustomerNameTest(){
        String customerName = "";
        Exception exception = assertThrows(FlooringMasteryServiceException.class, () -> {
             service.validateCustomerName(customerName);
        });

        String expectedMessage = "ERROR: You did not enter a valid Customer Name!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void checkAreaTest(){
        BigDecimal area = new BigDecimal("200.00");
        try{
            service.checkArea(area);
        } catch (FlooringMasteryServiceException e) {
            fail("Exception should not have been thrown!");
        }
    }

    @Test
    public void checkProductTypeTest(){
        String productType = new String("Hair");

        Exception exception = assertThrows(FlooringMasteryServiceException.class, () -> {
            service.checkProductType(productType);
        });

        String expectedMessage = "ERROR: " + productType + " is currently not in the product list.";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void createNewOrder(){

    }



}
