package com.company.salescafe.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.cuba.core.entity.StandardEntity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import javax.persistence.OneToOne;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import javax.validation.constraints.NotNull;
import com.haulmont.chile.core.annotations.NamePattern;

@NamePattern("%s %s %s|productType,productStatus,product")
@Table(name = "SALESCAFE_ORDER_CARD")
@Entity(name = "salescafe$OrderCard")
public class OrderCard extends StandardEntity {
    private static final long serialVersionUID = -9186619945350449410L;

    @NotNull
    @Column(name = "AMOUNT", nullable = false)
    protected Integer amount;

    @Composition
    @OnDelete(DeletePolicy.UNLINK)
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    protected Product product;

    @NotNull
    @Column(name = "PRODUCT_TYPE", nullable = false)
    protected Integer productType;

    @Column(name = "PRODUCT_STATUS")
    protected String productStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    protected Order order;

    @NotNull
    @Column(name = "PRICE", nullable = false)
    protected Integer price;

    public ProductStatus getProductStatus() {
        return productStatus == null ? null : ProductStatus.fromId(productStatus);
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus == null ? null : productStatus.getId();
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPrice() {
        return price;
    }

    public ProductTypes getProductType() {
        return productType == null ? null : ProductTypes.fromId(productType);
    }

    public void setProductType(ProductTypes productType) {
        this.productType = productType == null ? null : productType.getId();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }



}