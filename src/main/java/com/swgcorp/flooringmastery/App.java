/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.swgcorp.flooringmastery;

import FlooringMastery.controller.FlooringMasteryController;
import FlooringMastery.dao.OrdersDao;
import FlooringMastery.dao.OrdersDaoFileImpl;
import FlooringMastery.dao.ProductDao;
import FlooringMastery.dao.ProductDaoFileImpl;
import FlooringMastery.dao.TaxDao;
import FlooringMastery.dao.TaxDaoFileImpl;
import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.servicelayer.FlooringMasteryServiceLayer;
import FlooringMastery.servicelayer.FlooringMasteryServiceLayerImpl;
import FlooringMastery.servicelayer.InvalidProductException;
import FlooringMastery.servicelayer.InvalidStateException;
import FlooringMastery.servicelayer.NoOrdersExistException;
import FlooringMastery.ui.FlooringMasteryView;
import FlooringMastery.ui.UserIO;
import FlooringMastery.ui.UserIOConsoleImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Kanwal
 */
public class App {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();
    }
}
