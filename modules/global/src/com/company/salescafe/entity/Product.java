package com.company.salescafe.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s  %s|productName,residue")
@Table(name = "SALESCAFE_PRODUCT")
@Entity(name = "salescafe$Product")
public class Product extends StandardEntity {
    private static final long serialVersionUID = 1059390248643310069L;

    @Column(name = "PRODUCT_NAME")
    protected String productName;

    @Column(name = "WEIGHT")
    protected Integer weight;

    @Column(name = "PRODUCT_PRICE")
    protected Integer productPrice;


    @Column(name = "IS_AVAILABLE")
    protected Boolean isAvailable;

    @Column(name = "RESIDUE")
    protected Integer residue;

    @Column(name = "PRODUCT_TYPE")
    protected Integer productType;


    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }


    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }




    public void setProductType(ProductTypes productType) {
        this.productType = productType == null ? null : productType.getId();
    }

    public ProductTypes getProductType() {
        return productType == null ? null : ProductTypes.fromId(productType);
    }


    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setResidue(Integer residue) {
        this.residue = residue;
    }

    public Integer getResidue() {
        return residue;
    }


}