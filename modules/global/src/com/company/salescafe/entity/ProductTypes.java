package com.company.salescafe.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum ProductTypes implements EnumClass<Integer> {

    firstDish(10),
    secondDish(20),
    drinks(60),
    desert(30),
    bakery(40),
    service(50),
    salad(70);

    private Integer id;

    ProductTypes(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static ProductTypes fromId(Integer id) {
        for (ProductTypes at : ProductTypes.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}