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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Kanwal
 */
public class OrdersDaoFileImpl implements OrdersDao {

    private Map<LocalDate, HashMap<Integer, Orders>> orderMap = new HashMap<>();
    private static final String DELIMITER = ",";
    public static final String Order_FILE = "Orders_06012013.txt";
    private String ordersFileTitle = "OrderNumber,CustomerName,State,TaxRate,ProductType,Area,MaterialCostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total";

    @Override
    public List<Orders> getAllOrders(LocalDate date) throws FlooringMasteryPersistenceException, NoOrdersExistException {
        String dateAsString = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        File file = new File("Orders_" + dateAsString + ".txt");
        if (orderMap.get(date) != null) {
            return orderMap.get(date).values().stream().collect(Collectors.toList());
        } else if (file.exists()) {
            loadOrders(date);
        } else {
            throw new NoOrdersExistException("no orders exist for: " + date);
        }

        return orderMap.get(date)
                .values()
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public Orders getOrder(LocalDate date, int orderNumber) throws FlooringMasteryPersistenceException, NoOrdersExistException {
        String dateAsString = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));
        File file = new File("Orders_" + dateAsString + ".txt");
        if (orderMap.get(date) != null) {
            return orderMap.get(date).get(orderNumber);
        } else if (file.exists()) {
            loadOrders(date);
        }
        if (orderMap.get(date) == null || orderMap.get(date).get(orderNumber) == null) {
            throw new NoOrdersExistException("no orders exist for: " + date);
        }

        return orderMap.get(date)
                .get(orderNumber);
    }

    @Override
    public void addOrder(Orders order) throws FlooringMasteryPersistenceException, InvalidProductException, InvalidStateException {
        String dateAsString = order.getDate().format(DateTimeFormatter.ofPattern("MMddyyyy"));

        File file = new File("Orders_" + dateAsString + ".txt");
        if (orderMap.get(order.getDate()) != null) {
            orderMap.get(order.getDate()).put(order.getOrderNumber(), order);
        } else if (file.exists()) {
            loadOrders(order.getDate());
            orderMap.get(order.getDate()).put(order.getOrderNumber(), order);
        } else {//created a temporary hashmap to store values into the master ordermap.
            HashMap<Integer, Orders> newOrderInfo = new HashMap<>();
            newOrderInfo.put(order.getOrderNumber(), order);
            orderMap.put(order.getDate(), newOrderInfo);
        }
    }

    @Override
    public void removeOrder(LocalDate date, int orderNumber) {
        orderMap.get(date).remove(orderNumber);
    }

    @Override
    public void loadOrders(LocalDate date) throws FlooringMasteryPersistenceException {
        Scanner scanner;

        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMddyyyy"));

        String FileName = "Orders_" + formattedDate + ".txt";
        try {
            scanner = new Scanner(new BufferedReader(new FileReader(FileName)));

        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load data into memory.", e);
        }

        String currentLine;

        String[] currentTokens;
        int loop = 1;
        HashMap<Integer, Orders> innerMap = new HashMap<>();
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (loop != 1) {
                Orders order = new Orders(Integer.parseInt(currentTokens[0]));
                order.setDate(date);
                order.setOrderNumber(Integer.parseInt(currentTokens[0]));
                order.setCustomerName(currentTokens[1]);
                Tax tax = new Tax(currentTokens[2]);
                tax.setState(currentTokens[2]);
                tax.setTaxRate(new BigDecimal(currentTokens[3]));
                order.setTax(tax);

                Product product = new Product(currentTokens[4]);
                product.setProductType(currentTokens[4]);
                order.setArea(new BigDecimal(currentTokens[5]));
                product.setMaterialCostPerSqft(new BigDecimal(currentTokens[6]));
                product.setLaborCostPerSqFt(new BigDecimal(currentTokens[7]));
                order.setProductAndCalculate(product);
                order.setMaterialCost(new BigDecimal(currentTokens[8]));
                order.setLaborCost(new BigDecimal(currentTokens[9]));
                order.setTotalTax(new BigDecimal(currentTokens[10]));
                order.setTotal(new BigDecimal(currentTokens[11]));
                innerMap.put(order.getOrderNumber(), order);
            }
            loop++;

        }
        orderMap.put(date, innerMap);
        scanner.close();

    }

    @Override
    public void writeOrders() throws FlooringMasteryPersistenceException, NoOrdersExistException, InvalidConfigurationException {
        if (loadConfig().equalsIgnoreCase("prod")) {
            PrintWriter out;
            Set<LocalDate> fileNames = orderMap.keySet();

            for (LocalDate date : fileNames) {

                try {
                    String formattedDate = "Orders_" + date.format(DateTimeFormatter.ofPattern("MMddyyyy")) + ".txt";
                    out = new PrintWriter(new FileWriter(formattedDate));
                } catch (IOException e) {
                    throw new FlooringMasteryPersistenceException("Could not save Flooring Mastery data.", e);
                }
                out.println(ordersFileTitle);

                List<Orders> OrderList = this.getAllOrders(date);
                for (Orders currentOrder : OrderList) {
                    out.println(currentOrder.getOrderNumber() + DELIMITER
                            + currentOrder.getCustomerName() + DELIMITER
                            + currentOrder.getTax().getState() + DELIMITER
                            + currentOrder.getTax().getTaxRate() + DELIMITER
                            + currentOrder.getProduct().getProductType() + DELIMITER
                            + currentOrder.getArea() + DELIMITER
                            + currentOrder.getProduct().getMaterialCostPerSqft() + DELIMITER
                            + currentOrder.getProduct().getLaborCostPerSqFt() + DELIMITER
                            + currentOrder.getMaterialCost() + DELIMITER
                            + currentOrder.getLaborCost() + DELIMITER
                            + currentOrder.getTotalTax() + DELIMITER
                            + currentOrder.getTotal());

                    out.flush();
                }
                out.close();
            }

        } else if (loadConfig().equalsIgnoreCase("training")) {
        } else {
            throw new InvalidConfigurationException(
                    "Please enter the correct configuration type.");
        }

    }

    @Override
    public int loadNextOrder() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("LastOrderNumber")));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load data into memory.", e);
        }
        String currentLine;
        currentLine = scanner.nextLine();
        scanner.close();
        int lastOrderNumber = Integer.parseInt(currentLine);
        int nextOrderNumber = lastOrderNumber + 1;
        writeNextOrder(nextOrderNumber);
        return nextOrderNumber;
    }

    private void writeNextOrder(int nextOrderNumber) throws FlooringMasteryPersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter("LastOrderNumber"));
        } catch (IOException e) {
            throw new FlooringMasteryPersistenceException("Could not save data.", e);
        }
        out.print(nextOrderNumber);
        out.flush();
        out.close();
    }

    private String loadConfig() throws FlooringMasteryPersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("Configuration.txt")));
        } catch (FileNotFoundException e) {
            throw new FlooringMasteryPersistenceException(
                    "Could not load data into memory.", e);
        }
        String currentLine;
        currentLine = scanner.nextLine();
        scanner.close();
        String configuration = currentLine;

        return configuration;
    }

    //this method is used for OrdersDaoTest only
    @Override
    public Set<LocalDate> getOrderMapkeyset() {
        return orderMap.keySet();
    }
}
