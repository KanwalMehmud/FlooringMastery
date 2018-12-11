package FlooringMastery.controller;

import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.dao.InvalidConfigurationException;
import FlooringMastery.dto.Orders;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import FlooringMastery.servicelayer.FlooringMasteryServiceLayer;
import FlooringMastery.servicelayer.InvalidProductAndStateException;
import FlooringMastery.servicelayer.InvalidProductException;
import FlooringMastery.servicelayer.InvalidStateException;
import FlooringMastery.servicelayer.NoOrdersExistException;
import FlooringMastery.ui.FlooringMasteryView;
import java.time.LocalDate;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kanwal
 */
public class FlooringMasteryController {

    private FlooringMasteryView view;
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryView view, FlooringMasteryServiceLayer service) {

        this.view = view;
        this.service = service;
    }

    public void run() throws Exception {
        service.loadProductFiles();
        service.loadTaxFiles();
        boolean keepGoing = true;
        int menuSelection;
        while (keepGoing) {
            menuSelection = userSelection();
            switch (menuSelection) {
                case 1:
                    try {
                        displayOrders();
                    } catch (FlooringMasteryPersistenceException | NoOrdersExistException e) {
                        view.printMessage(e.getMessage());
                        run();
                    }

                    break;
                case 2:
                    try {
                        addOrder();
                    } catch (FlooringMasteryPersistenceException
                            | InvalidProductException
                            | InvalidStateException
                            | NoOrdersExistException
                            | InvalidProductAndStateException e) {
                        view.printMessage(e.getMessage());
                        run();
                    }

                    break;
                case 3:
                    try {
                        editOrder();
                    } catch (FlooringMasteryPersistenceException
                            | InvalidProductException
                            | InvalidStateException
                            | NoOrdersExistException
                            | InvalidProductAndStateException e) {
                        view.printMessage(e.getMessage());
                        run();
                    }
                    break;
                case 4:
                    try {
                        removeOrder();
                    } catch (FlooringMasteryPersistenceException | NoOrdersExistException e) {
                        view.printMessage(e.getMessage());
                        run();
                    }
                    break;
                case 5:
                    save();
                    break;
                case 6:
                    exit();
                    keepGoing = false;
                    break;
                default:
                    unknownCommand();
                    run();
            }
        }

    }

    private int userSelection() {
        return view.userSelection();

    }

    private void displayOrders() throws FlooringMasteryPersistenceException, NoOrdersExistException {
        LocalDate date = view.getDate();
        view.displayOrdersBanner();
        List<Orders> OrderList = service.getAllOrders(date);
        view.displayOrders(OrderList);
    }

    private void addOrder() throws FlooringMasteryPersistenceException, InvalidProductException, InvalidStateException, NoOrdersExistException, InvalidProductAndStateException {
        view.addOrderBanner();
        int nextOrderNumber = service.loadNextOrder();
        List<Product> productList = service.getAllProducts();
        List<Tax> stateList = service.getAllTax();
        Orders orderInfo = view.addOrder(nextOrderNumber, productList, stateList);
        service.addOrder(orderInfo);
        view.addEditRemovceOrderSuccessBanner();

    }

    private void editOrder() throws FlooringMasteryPersistenceException, NoOrdersExistException, InvalidProductException, InvalidStateException, InvalidProductAndStateException, Exception {
        view.editOrderBanner();
        LocalDate date = view.getDate();
        int orderNumber = view.getOrderNumber();
        try {
            Orders oldOrder = service.getOrder(date, orderNumber);
            List<Product> productList = service.getAllProducts();
            List<Tax> stateList = service.getAllTax();
            Orders edittedOrder = view.editOrder(oldOrder, productList, stateList);
            service.editOrder(oldOrder, edittedOrder);
            view.addEditRemovceOrderSuccessBanner();
        } catch (Exception e) {
            view.printMessage(e.getMessage());
            run();
        }
    }

    private void removeOrder() throws FlooringMasteryPersistenceException, NoOrdersExistException {
        view.removeOrderBanner();
        LocalDate orderDate = view.getDate();
        int orderNumber = view.orderNumber();
        Orders findOrder = service.getOrder(orderDate, orderNumber);
        boolean userInput = view.deleteOrder(findOrder);
        if (userInput) {
            service.removeOrder(orderDate, orderNumber);

        }

        view.addEditRemovceOrderSuccessBanner();
    }

    private void unknownCommand() {
        view.displayUnknownCommandMessage();

    }

    private void exit() {
        view.exitBanner();
    }

    private void save() throws NoOrdersExistException, InvalidConfigurationException {
        service.writeOrders();
    }

}
