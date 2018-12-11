/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Product;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author Kanwal
 */
public class ProductDaoStubImpl implements ProductDao {

    Product product1;
    Product product2;
    Map<String, Product> productList = new HashMap<>();

    public ProductDaoStubImpl() {
        product1 = new Product("Wood");
        product1.setProductType("Wood");
        product1.setLaborCostPerSqFt(new BigDecimal("4.75"));
        product1.setMaterialCostPerSqft(new BigDecimal("5.15"));
        productList.put(product1.getProductType(), product1);
        product2 = new Product("Tile");
        product2.setProductType("Tile");
        product2.setLaborCostPerSqFt(new BigDecimal("4.15"));
        product2.setMaterialCostPerSqft(new BigDecimal("3.50"));
        productList.put(product2.getProductType(), product2);
    }

    @Override
    public void loadProduct() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Product> getAllProducts() {
        return productList.values().stream().collect(Collectors.toList());
    }

    @Override
    public Product getProductInfo(String productType) {
        return productList.get(productType);
    }

}
