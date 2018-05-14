package com.company.salescafe.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum TypeOfCustomer implements EnumClass<Integer> {

    family(1),
    man(2),
    woman(3),
    youngMan(4),
    youngGirl(5),
    girlCompany(6),
    manCompany(7);

    private Integer id;

    TypeOfCustomer(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static TypeOfCustomer fromId(Integer id) {
        for (TypeOfCustomer at : TypeOfCustomer.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}