/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FlooringMastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author Kanwal
 */
public class Product {

    private String productType;
    private BigDecimal materialCostPerSqft;
    private BigDecimal laborCostPerSqFt;

    public Product(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getMaterialCostPerSqft() {
        return materialCostPerSqft;
    }

    public void setMaterialCostPerSqft(BigDecimal materialCostPerSqft) {
        this.materialCostPerSqft = materialCostPerSqft;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

}
