/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Orders;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import FlooringMastery.servicelayer.InvalidProductException;
import FlooringMastery.servicelayer.InvalidStateException;
import FlooringMastery.servicelayer.NoOrdersExistException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Kanwal
 */
public class OrdersDaoStubImpl implements OrdersDao {

    Orders onlyOrder;
    private Map<LocalDate, HashMap<Integer, Orders>> orderList = new HashMap<>();

    public OrdersDaoStubImpl() {
        String stringDate = "10012017";
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MMddyyyy"));

        onlyOrder = new Orders(1);
        onlyOrder.setOrderNumber(1);
        onlyOrder.setDate(date);
        onlyOrder.setCustomerName("Joe");
        Tax tax = new Tax("PA");
        tax.setState("PA");
        tax.setTaxRate(new BigDecimal("6.75"));
        onlyOrder.setTax(tax);
        onlyOrder.setArea(new BigDecimal("550"));
        Product product = new Product("Wood");
        product.setProductType("Wood");
        product.setLaborCostPerSqFt(new BigDecimal("4.75"));
        product.setMaterialCostPerSqft(new BigDecimal("5.15"));
        onlyOrder.setProductAndCalculate(product);
        onlyOrder.SetcalcatingOrderTotal();
        HashMap<Integer, Orders> newOrderInfo = new HashMap<>();
        newOrderInfo.put(onlyOrder.getOrderNumber(), onlyOrder);
        orderList.put(onlyOrder.getDate(), newOrderInfo);

    }

    @Override
    public List<Orders> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException, NoOrdersExistException {
        return orderList.get(date)
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Orders getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException, NoOrdersExistException {
        return orderList.get(date).get(orderNumber);
    }

    @Override
    public void addOrder(Orders order) throws FlooringMasteryPersistenceException, InvalidProductException, InvalidStateException {
        if (orderList.get(order.getDate()) != null) {
            orderList.get(order.getDate()).put(order.getOrderNumber(), order);
        } else {
//created a temporary hashmap to store values into the master ordermap.
            HashMap<Integer, Orders> newOrderInfo = new HashMap<>();
            newOrderInfo.put(order.getOrderNumber(), order);
            orderList.put(order.getDate(), newOrderInfo);
        }
    }

    @Override
    public void removeOrder(LocalDate date, int orderNumber) {
        orderList.get(date).remove(orderNumber);
    }

    @Override
    public void loadOrders(LocalDate date) throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    @Override
    public void writeOrders() throws FlooringMasteryPersistenceException, NoOrdersExistException, InvalidConfigurationException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

    }

    //this method is used for OrdersDaoTest only
    @Override
    public Set<LocalDate> getOrderMapkeyset() {
        return orderList.keySet();
    }

    @Override
    public int loadNextOrder() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
