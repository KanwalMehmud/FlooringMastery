/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 *
 * @author Kanwal
 */
public class ProductDaoFileImpl implements ProductDao {

    Map<String, Product> products = new HashMap<>();
    private static final String DELIMITER = ",";

    @Override
    public List<Product> getAllProducts() {
        return products.values().stream().collect(Collectors.toList());
    }

    @Override
    public Product getProductInfo(String productType) {
        return products.get(productType);
    }

    @Override
    public void loadProduct() throws FlooringMasteryPersistenceException {
        Scanner scanner = null;
        try {
            scanner = new Scanner(new BufferedReader(new FileReader("Products.txt")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String currentLine;

        String[] currentTokens;
        int loop = 1;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (loop != 1) {
                Product product = new Product(currentTokens[0]);
                product.setProductType((currentTokens[0]));
                product.setMaterialCostPerSqft(new BigDecimal(currentTokens[1]));
                product.setLaborCostPerSqFt(new BigDecimal(currentTokens[2]));

                products.put(product.getProductType(), product);
            }
            loop++;

        }
    }

}
