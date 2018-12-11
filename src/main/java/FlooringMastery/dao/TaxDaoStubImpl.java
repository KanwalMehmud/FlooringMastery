/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Tax;
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
public class TaxDaoStubImpl implements TaxDao {

    Tax onlyTax;
    Map<String, Tax> taxList = new HashMap<>();

    public TaxDaoStubImpl() {
        onlyTax = new Tax("PA");
        onlyTax.setState("PA");
        onlyTax.setTaxRate(new BigDecimal("6.75"));
        taxList.put(onlyTax.getState(), onlyTax);
    }

    @Override
    public void loadTax() throws FlooringMasteryPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Tax> getAllTax() {
        return taxList.values().stream().collect(Collectors.toList());
    }

    @Override
    public Tax getTaxInfo(String taxInfo) {
        return taxList.get(taxInfo);
    }

}
