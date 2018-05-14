package com.company.salescafe.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum DaysOfWeek implements EnumClass<Integer> {

    MONDEY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private Integer id;

    DaysOfWeek(Integer value) {
        this.id = value;
    }

    public Integer getId() {
        return id;
    }

    @Nullable
    public static DaysOfWeek fromId(Integer id) {
        for (DaysOfWeek at : DaysOfWeek.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}