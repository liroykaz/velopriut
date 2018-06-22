package com.company.salescafe.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum OrderStatus implements EnumClass<Integer> {

    isaccepted(10),
    isCompleted(30);

    private Integer id;

    OrderStatus(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static OrderStatus fromId(Integer id) {
        for (OrderStatus at : OrderStatus.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}