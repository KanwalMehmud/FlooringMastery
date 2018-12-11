/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.ui;

import FlooringMastery.dto.Orders;
import FlooringMastery.dto.Product;
import FlooringMastery.dto.Tax;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Kanwal
 */
public class FlooringMasteryView {

    private UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int userSelection() {
        io.print("*******************************");
        io.print("<<Flooring Program>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Save Current Work");
        io.print("6. Quit");
        io.print("*******************************");
        return io.readInt("Select from the above choices.", 1, 6);
    }

    public Orders addOrder(int orderNumber, List<Product> productList, List<Tax> stateList) {
        Orders addOrder = new Orders(orderNumber);
        io.print("Your order number is: " + orderNumber);
        addOrder.setOrderNumber(orderNumber);
        LocalDate orderDate = io.readDate("Enter an order date: ");
        String customerName = io.readString("Enter Customer Name: ");

        io.print("Please select from the following states: ");
        stateList.stream().forEach(s -> io.print(s.getState()));
        String state = io.readString("Enter State: ").toUpperCase();

        io.print("Please select from the following products: ");
        productList.stream().forEach(s -> io.print(s.getProductType()));
        String productType = io.readString("Enter Product Type");
        if (productType.length() > 1) {
            productType = productType.substring(0, 1).toUpperCase() + productType.substring(1).toLowerCase();
        }
        addOrder.setDate(orderDate);
        BigDecimal area = io.readBigDecimal("Enter Area: ");
        addOrder.setArea(area);
        addOrder.setCustomerName(customerName);

        Product product = new Product(productType);
        addOrder.setProduct(product);
        Tax tax = new Tax(state);
        addOrder.setTax(tax);
        return addOrder;
    }

    public Orders editOrder(Orders oldOrder, List<Product> productList, List<Tax> stateList) {
        Orders edittedOrder = new Orders(oldOrder.getOrderNumber());

        if (oldOrder != null) {
            edittedOrder.setOrderNumber(oldOrder.getOrderNumber());
            LocalDate date = io.readDateEdit("Enter a date in the format MMddyyyy or hit enter to continue (" + oldOrder.getDate() + "):");
            String customerName = io.readString("Enter Customer Name or hit enter to continue (" + oldOrder.getCustomerName() + "):");

            io.print("Please select from the following states: ");
            stateList.stream().forEach(s -> io.print(s.getState()));
            String state = io.readString("Enter State or hit enter to continue (" + oldOrder.getTax().getState() + "):").toUpperCase();

            io.print("Please select from the following products: ");
            productList.stream().forEach(s -> io.print(s.getProductType()));
            String productType = io.readString("Enter Product Type or hit enter to continue (" + oldOrder.getProduct().getProductType() + "):");
            if (productType.length() > 1) {
                productType = productType.substring(0, 1).toUpperCase() + productType.substring(1).toLowerCase();
            }
            BigDecimal area = io.readBigDecimalEdit("Enter Area or hit enter to continue (" + oldOrder.getArea() + "):");
            //setting the editted order into the properties stated in dto.
            edittedOrder.setDate(date);
            edittedOrder.setCustomerName(customerName);
            edittedOrder.setArea(area);
            Product product = new Product(productType);
            edittedOrder.setProduct(product);
            Tax tax = new Tax(state);
            edittedOrder.setTax(tax);

        } else {
            io.print("This order does not exist for this date.");
        }
        return edittedOrder;
    }

    public int orderNumber() {
        return io.readInt("Enter orderNumber: ");
    }

    public void displayOrdersBanner() {
        io.print("Orders for the selected date:");
    }

    public void addOrderBanner() {
        io.print("******Add an Order******");
    }

    public void editOrderBanner() {
        io.print("*****Edit an Order******");
    }

    public void removeOrderBanner() {
        io.print("*****Remove an Order*****");
    }

    public void exitBanner() {
        io.print("GoodBye!");
    }

    public void addEditRemovceOrderSuccessBanner() {
        io.readString("Success! Please hit enter to continue.");

    }

    public int getOrderNumber() {
        return io.readInt("Enter order number:");
    }

    public void printMessage(String msg) {
        io.print(msg);
    }

    public LocalDate getDate() {
        return io.readDate("Enter a date in the format MMDDYYYY:");
    }

    public void displayOrders(List<Orders> orderList) {

        orderList.stream().forEach(order -> io.print(
                "order number: " + order.getOrderNumber()
                + "\ncustomer's name: " + order.getCustomerName()
                + "\nstate: " + order.getTax().getState()
                + "\ntax rate: " + order.getTax().getTaxRate()
                + "\nproduct type: " + order.getProduct().getProductType()
                + "\narea: " + order.getArea()
                + "\nmaterial cost per square foot: " + order.getProduct().getMaterialCostPerSqft()
                + "\nlabor cost per square foot: " + order.getProduct().getLaborCostPerSqFt()
                + "\nmaterial cost: " + order.getMaterialCost()
                + "\nlabor cost: " + order.getLaborCost()
                + "\ntax: " + order.getTotalTax()
                + "\ntotal: " + order.getTotal()));
    }

    public boolean deleteOrder(Orders deleteOrder) {
        io.print("Customer Name: " + deleteOrder.getCustomerName());
        io.print("State: " + deleteOrder.getTax().getState());
        io.print("Product Type: " + deleteOrder.getProduct().getProductType());
        io.print("Area: " + deleteOrder.getArea());
        String userInput = io.readString("Are you sure you want to remove this order? Y/N");
        return userInput.equalsIgnoreCase("Y");
    }

    public void displayUnknownCommandMessage() {
        io.print("Please select from the options in the menu");
    }

}
