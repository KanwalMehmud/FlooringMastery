/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.servicelayer;

import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.dao.InvalidConfigurationException;
import FlooringMastery.dto.Orders;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Kanwal
 */
public interface FlooringMasteryServiceLayer {

    List<Orders> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException, NoOrdersExistException;

    Orders getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException, NoOrdersExistException;

    public void addOrder(Orders order) throws FlooringMasteryPersistenceException, InvalidProductException, InvalidStateException, InvalidProductAndStateException;

    public void editOrder(Orders oldOrder, Orders edittedOrder) throws InvalidProductException, InvalidStateException, FlooringMasteryPersistenceException, InvalidProductAndStateException;

    public void removeOrder(LocalDate date, int orderNumber) throws NoOrdersExistException, FlooringMasteryPersistenceException;

    public void loadOrders(LocalDate date);

    public void writeOrders() throws NoOrdersExistException, InvalidConfigurationException;

    public void loadProductFiles() throws FlooringMasteryPersistenceException;

    public void loadTaxFiles() throws FlooringMasteryPersistenceException;

    public int loadNextOrder() throws FlooringMasteryPersistenceException;

    public List<Product> getAllProducts();

    public List<Tax> getAllTax();
}
