/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Orders;
import FlooringMastery.servicelayer.InvalidProductException;
import FlooringMastery.servicelayer.InvalidStateException;
import FlooringMastery.servicelayer.NoOrdersExistException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Kanwal
 */
public interface OrdersDao {

    List<Orders> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException, NoOrdersExistException;

    Orders getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException, NoOrdersExistException;

    public void addOrder(Orders order) throws FlooringMasteryPersistenceException, InvalidProductException, InvalidStateException;

    public void removeOrder(LocalDate date, int orderNumber);

    public void loadOrders(LocalDate date) throws FlooringMasteryPersistenceException;

    public void writeOrders() throws FlooringMasteryPersistenceException, NoOrdersExistException, InvalidConfigurationException;

    public int loadNextOrder() throws FlooringMasteryPersistenceException;

    Set<LocalDate> getOrderMapkeyset();

}
