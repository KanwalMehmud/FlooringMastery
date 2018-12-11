/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.servicelayer;

import FlooringMastery.dao.OrdersDao;
import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.dao.InvalidConfigurationException;
import FlooringMastery.dao.ProductDao;
import FlooringMastery.dao.TaxDao;
import FlooringMastery.dto.Orders;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * * @author Kanwal
 */
public class FlooringMasteryServiceLayerImpl implements FlooringMasteryServiceLayer {

    private OrdersDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;

    public FlooringMasteryServiceLayerImpl(OrdersDao orderDao, ProductDao productDao, TaxDao taxDao) {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public void loadProductFiles() throws FlooringMasteryPersistenceException {
        productDao.loadProduct();

    }

    @Override
    public void loadTaxFiles() throws FlooringMasteryPersistenceException {
        taxDao.loadTax();
    }

    @Override
    public List<Orders> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException, NoOrdersExistException {

        return orderDao.getAllOrders(date);
    }

    @Override
    public Orders getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException, NoOrdersExistException {
        return orderDao.getOrder(date, orderNumber);
    }

    @Override
    public void addOrder(Orders order) throws FlooringMasteryPersistenceException, InvalidProductException, InvalidStateException, InvalidProductAndStateException {

        if (productDao.getProductInfo(order.getProduct().getProductType()) == null && taxDao.getTaxInfo(order.getTax().getState()) == null) {
            throw new InvalidProductAndStateException("Product type and State do not exist");
        }

        if (productDao.getProductInfo(order.getProduct().getProductType())
                == null) {
            throw new InvalidProductException("Product type does not exist.");
        }
        if (taxDao.getTaxInfo(order.getTax().getState()) == null) {
            throw new InvalidStateException("Invalid State.");
        }
        Product productInfo = productDao.getProductInfo(order.getProduct().getProductType());
        order.getProduct().setLaborCostPerSqFt(productInfo.getLaborCostPerSqFt());
        order.getProduct().setMaterialCostPerSqft(productInfo.getMaterialCostPerSqft());
        Tax taxInfo = taxDao.getTaxInfo(order.getTax().getState());
        order.getTax().setTaxRate(taxInfo.getTaxRate());
        order.setProductAndCalculate(productInfo);
        order.SetcalcatingOrderTotal();
        orderDao.addOrder(order);
    }

    @Override
    public void editOrder(Orders oldOrder, Orders edittedOrder) throws FlooringMasteryPersistenceException, InvalidProductException, InvalidStateException, InvalidProductAndStateException {

// new product validation
        if (!edittedOrder.getProduct().getProductType().equalsIgnoreCase("")) {
            if (productDao.getProductInfo(edittedOrder.getProduct().getProductType()) == null) {
                throw new InvalidProductException("Product type does not exist.");
            }
        }

// new state validation
        if (!edittedOrder.getTax().getState().equalsIgnoreCase("")) {
            if (taxDao.getTaxInfo(edittedOrder.getTax().getState()) == null) {
                throw new InvalidStateException("Invalid State.");
            }
        }

// store local date from old order to remove it from map
        LocalDate oldDate = oldOrder.getDate();

        if (edittedOrder.getArea().compareTo(BigDecimal.ZERO) < 0) {
        } else {
            oldOrder.setArea(edittedOrder.getArea());
        }
        if (edittedOrder.getCustomerName().equalsIgnoreCase("")) {
        } else {
            oldOrder.setCustomerName(edittedOrder.getCustomerName());
        }
        if (edittedOrder.getTax().getState().equalsIgnoreCase("")) {
        } else {
            oldOrder.setTax(edittedOrder.getTax());
        }
        if (edittedOrder.getProduct().getProductType().equalsIgnoreCase("")) {
        } else {
            oldOrder.setProduct(edittedOrder.getProduct());
        }
        if (edittedOrder.getDate() != null) {
            oldOrder.setDate(edittedOrder.getDate());
        }
        addOrder(oldOrder);
// remove order from previous date
        if (edittedOrder.getDate() != null) {
            orderDao.removeOrder(oldDate, oldOrder.getOrderNumber());
        }
    }

    @Override
    public void removeOrder(LocalDate date, int orderNumber) {
        orderDao.removeOrder(date, orderNumber);
    }

    @Override
    public void loadOrders(LocalDate date) {
        try {
            orderDao.loadOrders(date);
        } catch (FlooringMasteryPersistenceException ex) {
            Logger.getLogger(FlooringMasteryServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void writeOrders() throws NoOrdersExistException, InvalidConfigurationException {
        try {
            orderDao.writeOrders();
        } catch (FlooringMasteryPersistenceException ex) {
            Logger.getLogger(FlooringMasteryServiceLayerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public int loadNextOrder() throws FlooringMasteryPersistenceException {
        return orderDao.loadNextOrder();
    }

    @Override
    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public List<Tax> getAllTax() {
        return taxDao.getAllTax();
    }

}
