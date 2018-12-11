/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dao;

import FlooringMastery.dto.Tax;
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
public class TaxDaoFileImpl implements TaxDao {

    Map<String, Tax> tax = new HashMap<>();
    private static final String DELIMITER = ",";

    @Override
    public void loadTax() throws FlooringMasteryPersistenceException {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new BufferedReader(new FileReader("Taxes.txt")));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TaxDaoFileImpl.class.getName()).log(Level.SEVERE, null, ex);
        }

        String currentLine;
        String[] currentTokens;
        int loop = 1;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (loop != 1) {
                Tax taxes = new Tax(currentTokens[0]);
                taxes.setState(currentTokens[0]);
                taxes.setTaxRate(new BigDecimal((currentTokens[1])));

                tax.put(taxes.getState(), taxes);

            }
            loop++;

        }
    }

    @Override
    public List<Tax> getAllTax() {
        return tax.values().stream().collect(Collectors.toList());
    }

    @Override
    public Tax getTaxInfo(String taxInfo) {
        return tax.get(taxInfo);
    }

}
