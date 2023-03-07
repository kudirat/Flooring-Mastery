package com.sg.flooringorders.dao;

import com.sg.flooringorders.dao.FlooringOrderDao;
import com.sg.flooringorders.dao.FlooringOrderDaoImpl;
import com.sg.flooringorders.dao.ProductFileDao;
import com.sg.flooringorders.service.FlooringMasteryServiceLayer;
import org.junit.jupiter.api.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.fail;

public class FlooringOrderDaoImplTest {

    FlooringOrderDao testDao;
    FlooringMasteryServiceLayer testService;


    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
        String testFolder = "src\\main\\testOrders";
         testDao = new FlooringOrderDaoImpl(testFolder);

    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createOrderFileNameWithPastDateTest() {
        String dateStr = "2023-01-03";
        LocalDate orderDate = LocalDate.parse(dateStr);
        fail("The date must be in the present");
    }


}
