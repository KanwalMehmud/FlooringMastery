/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.servicelayer;

import FlooringMastery.dao.FlooringMasteryPersistenceException;
import FlooringMastery.dto.Orders;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Kanwal
 */
public class FlooringMasteryServiceLayerTest {

    private FlooringMasteryServiceLayer service;

    public FlooringMasteryServiceLayerTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringMasteryServiceLayer.class);

    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getAllOrders method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetAllOrders() throws Exception {
    }

    /**
     * Test of getOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testGetOrder() throws Exception {

        String stringDate = "10012017";
        LocalDate date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("MMddyyyy"));

        assertEquals(1, service.getAllOrders(date).size());

    }

    /**
     * Test of addOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testAddOrder() throws Exception {
        String stringDate1 = "06272017";
        LocalDate date = LocalDate.parse(stringDate1, DateTimeFormatter.ofPattern("MMddyyyy"));

        Orders order1 = new Orders(2);
        order1.setOrderNumber(2);
        order1.setDate(date);
        order1.setCustomerName("Joel");
        order1.setArea(new BigDecimal("250"));
        Tax tax1 = new Tax("PA");
        tax1.setState("PA");
        order1.setTax(tax1);
        Product product1 = new Product("Wood");
        product1.setProductType("Wood");
        order1.setProduct(product1);
        service.addOrder(order1);

        assertEquals(new BigDecimal("4.75"), service.getOrder(order1.getDate(), order1.getOrderNumber()).getProduct().getLaborCostPerSqFt());
        assertEquals(new BigDecimal("6.75"), service.getOrder(order1.getDate(), order1.getOrderNumber()).getTax().getTaxRate());

//Testing with invalid data
        Orders order2 = new Orders(2);
        order2.setOrderNumber(2);
        order2.setDate(date);
        order2.setCustomerName("Eric");
        order2.setArea(new BigDecimal("250"));
        Tax tax2 = new Tax("MN");
        tax2.setState("MN");
        order2.setTax(tax2);
        Product product = new Product("Carpet");
        product.setProductType("Carpet");
        order2.setProduct(product);
        try {
            service.addOrder(order2);
            fail("Expected exception was not thrown");
        } catch (FlooringMasteryPersistenceException | InvalidProductAndStateException | InvalidProductException | InvalidStateException e) {
        }

    }

    /**
     * Test of editOrder method, of class FlooringMasteryServiceLayer.
     */
    @Test
    public void testEditOrder() throws Exception {
        String stringDate1 = "10012017";
        LocalDate date = LocalDate.parse(stringDate1, DateTimeFormatter.ofPattern("MMddyyyy"));

        String stringDate2 = "11052020";
        LocalDate date2 = LocalDate.parse(stringDate2, DateTimeFormatter.ofPattern("MMddyyyy"));

        Orders edittedOrder = new Orders(1);
        edittedOrder.setOrderNumber(1);
        edittedOrder.setDate(date2);
        edittedOrder.setCustomerName("Jenna");
        edittedOrder.setArea(new BigDecimal("30"));
        Tax tax = new Tax("PA");
        tax.setState("PA");
        edittedOrder.setTax(tax);
        Product product = new Product("Tile");
        product.setProductType("Tile");
        edittedOrder.setProduct(product);
        service.addOrder(edittedOrder);

        Orders oldOrder = service.getOrder(date, 1);
        service.editOrder(oldOrder, edittedOrder);

// Testing order date and product change 
        assertEquals(new BigDecimal("4.15"), service.getOrder(edittedOrder.getDate(), edittedOrder.getOrderNumber()).getProduct().getLaborCostPerSqFt());
        assertEquals(new BigDecimal("6.75"), service.getOrder(edittedOrder.getDate(), edittedOrder.getOrderNumber()).getTax().getTaxRate());

        try {
// Testing if order was removed from previous the date after the edit method
            service.getOrder(date, 1).getProduct().getProductType();
            fail("Exception was expected");
        } catch (Exception e) {
            return;
        }

    }

}
