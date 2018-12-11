/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Orders;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Kanwal
 */
public class OrdersDaoTest {

    private OrdersDao dao = new OrdersDaoFileImpl();

    public OrdersDaoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws Exception {
        Set<LocalDate> orderMapKeyset = dao.getOrderMapkeyset();
        for (LocalDate date : orderMapKeyset) {

            List<Orders> ordersList = dao.getAllOrders(date);
            for (Orders orders : ordersList) {
                dao.removeOrder(orders.getDate(), orders.getOrderNumber());
            }
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllOrders method, of class OrdersDao.
     */
    @Test
    public void testGetAllOrders() throws Exception {

        String stringDate1 = "06272017";
        LocalDate date = LocalDate.parse(stringDate1, DateTimeFormatter.ofPattern("MMddyyyy"));

        Orders order1 = new Orders(2);
        order1.setOrderNumber(2);
        order1.setDate(date);
        order1.setCustomerName("Joel");
        order1.setArea(new BigDecimal("250"));
        Tax tax1 = new Tax("PA");
        tax1.setState("PA");
        tax1.setTaxRate(new BigDecimal("6.75"));
        order1.setTax(tax1);
        Product product1 = new Product("Wood");
        product1.setProductType("Wood");
        product1.setMaterialCostPerSqft(new BigDecimal("5.15"));
        product1.setLaborCostPerSqFt(new BigDecimal("4.75"));

        order1.setProductAndCalculate(product1);
        order1.SetcalcatingOrderTotal();
        dao.addOrder(order1);

        Orders order2 = new Orders(3);
        order2.setOrderNumber(3);
        order2.setDate(date);
        order2.setCustomerName("Joel");
        order2.setArea(new BigDecimal("250"));
        Tax tax2 = new Tax("PA");
        tax2.setState("PA");
        tax2.setTaxRate(new BigDecimal("6.75"));
        order2.setTax(tax2);
        Product product2 = new Product ("Wood");
        product2.setProductType("Wood");

        product2.setMaterialCostPerSqft(new BigDecimal("5.15"));
        product2.setLaborCostPerSqFt(new BigDecimal("4.75"));
       
        order2.setProductAndCalculate(product2);
        order2.SetcalcatingOrderTotal();
        dao.addOrder(order2);

        assertEquals(2, dao.getAllOrders(date).size());
    }

    /**
     * Test of addOrder method and of getOrder method, of class OrdersDao.
     */
    @Test
    public void testAddGetOrder() throws Exception {
        Orders order = new Orders(1);
        String stringDate = "10012017";
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MMddyyyy"));
        order.setOrderNumber(1);
        order.setDate(date);
        order.setCustomerName("Joe");
        order.setArea(new BigDecimal("550"));
        Tax tax = new Tax("PA");
        tax.setState("PA");
        tax.setTaxRate(new BigDecimal("6.75"));
        order.setTax(tax);
        Product product = new Product("Wood");
        product.setProductType("Wood");

        product.setMaterialCostPerSqft(new BigDecimal("5.15"));
        product.setLaborCostPerSqFt(new BigDecimal("4.75"));
        order.setProductAndCalculate(product);

        order.SetcalcatingOrderTotal();
        dao.addOrder(order);

        Orders fromDao = dao.getOrder(order.getDate(), order.getOrderNumber());

        assertEquals(order, fromDao);
    }

    /**
     * Test of removeOrder method, of class OrdersDao.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        String stringDate1 = "06272017";
        LocalDate date = LocalDate.parse(stringDate1, DateTimeFormatter.ofPattern("MMddyyyy"));

        Orders order1 = new Orders(2);
        order1.setOrderNumber(2);
        order1.setDate(date);
        order1.setCustomerName("Joel");
        order1.setArea(new BigDecimal("250"));
        Tax tax1 = new Tax("PA");
        tax1.setState("PA");
        tax1.setTaxRate(new BigDecimal("6.75"));
        order1.setTax(tax1);
        Product product1 = new Product("Wood");
        product1.setProductType("Wood");
        product1.setMaterialCostPerSqft(new BigDecimal("5.15"));
        product1.setLaborCostPerSqFt(new BigDecimal("4.75"));

        order1.setProductAndCalculate(product1);
        order1.SetcalcatingOrderTotal();
        dao.addOrder(order1);

        Orders order2 = new Orders(3);
        order2.setOrderNumber(3);
        order2.setDate(date);
        order2.setCustomerName("Joel");
        order2.setArea(new BigDecimal("250"));
        Tax tax2 = new Tax("PA");
        tax2.setState("PA");
        tax2.setTaxRate(new BigDecimal("6.75"));
        order2.setTax(tax2);
        Product product2 = new Product("Wood");
        product2.setProductType("Wood");
        product2.setMaterialCostPerSqft(new BigDecimal("5.15"));
        product2.setLaborCostPerSqFt(new BigDecimal("4.75"));

        order2.setProductAndCalculate(product2);
        order2.SetcalcatingOrderTotal();
        dao.addOrder(order2);

        dao.removeOrder(order1.getDate(), order1.getOrderNumber());
        assertEquals(1, dao.getAllOrders(date).size());
        assertNull(dao.getOrder(order1.getDate(), order1.getOrderNumber()));

        dao.removeOrder(order1.getDate(), order2.getOrderNumber());
        assertEquals(0, dao.getAllOrders(date).size());
        assertNull(dao.getOrder(order1.getDate(), order2.getOrderNumber()));
    }
}
