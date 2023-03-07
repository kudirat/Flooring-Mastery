package com.sg.flooringorders;

import com.sg.flooringorders.controller.FlooringMasteryController;
import com.sg.flooringorders.dao.*;
import com.sg.flooringorders.service.FlooringMasteryServiceLayer;
import com.sg.flooringorders.service.FlooringMasteryServiceLayerImpl;
import com.sg.flooringorders.ui.FlooringMasteryView;
import com.sg.flooringorders.ui.UserIO;
import com.sg.flooringorders.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

     public static void main(String[] args) {
//        UserIO myIO = new UserIOConsoleImpl();
//        // Instantiate the View and wire the UserIO implementation into it
//        FlooringMasteryView myView = new FlooringMasteryView(myIO);
//        ProductFileDao productFileDao = new ProductFileDaoImpl();
//        TaxFileDao taxFileDao = new TaxFileDaoImpl();
//        FlooringOrderDao ordersFileDao = new FlooringOrderDaoImpl();
//        // Instantiate the DAO
//
//        // Instantiate the Service Layer and wire the DAO and Audit DAO into it
//        FlooringMasteryServiceLayer myService = new FlooringMasteryServiceLayerImpl(ordersFileDao, productFileDao, taxFileDao);
//        // Instantiate the Controller and wire the Service Layer into it
//        FlooringMasteryController controller = new FlooringMasteryController(myView, myService);
//        controller.run();  // Kick off the Controller

         //implementing Spring DI!
         ApplicationContext appContext
                 = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

         FlooringMasteryController controller = appContext.getBean("controller", FlooringMasteryController.class);
         controller.run();
    }



}
