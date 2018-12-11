/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Product;
import java.util.List;

/**
 *
 * @author Kanwal
 */
public interface ProductDao {

    public void loadProduct() throws FlooringMasteryPersistenceException;

    public List<Product> getAllProducts();

    public Product getProductInfo(String productType);
}
