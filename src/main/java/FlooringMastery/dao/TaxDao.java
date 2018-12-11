/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Tax;
import java.util.List;

/**
 *
 * @author Kanwal
 */
public interface TaxDao {

    public void loadTax() throws FlooringMasteryPersistenceException;

    public List<Tax> getAllTax();

    public Tax getTaxInfo(String taxInfo);

}
