package com.company.salescafe.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import com.haulmont.cuba.core.entity.StandardEntity;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Table(name = "SALESCAFE_WORK_DAY")
@Entity(name = "salescafe$WorkDay")
public class WorkDay extends StandardEntity {
    private static final long serialVersionUID = -1573243400626942354L;

    @Column(name = "TOTAL_PROFIT")
    protected Integer totalProfit;

    @Column(name = "DAY_OF_WEEK")
    protected Integer dayOfWeek;

    @OneToMany(mappedBy = "workDay")
    protected List<Order> orders;

    @Temporal(TemporalType.DATE)
    @Column(name = "WORK_DATE")
    protected Date workDate;

    public void setDayOfWeek(DaysOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek == null ? null : dayOfWeek.getId();
    }

    public DaysOfWeek getDayOfWeek() {
        return dayOfWeek == null ? null : DaysOfWeek.fromId(dayOfWeek);
    }


    public void setWorkDate(Date workDate) {
        this.workDate = workDate;
    }

    public Date getWorkDate() {
        return workDate;
    }


    public void setTotalProfit(Integer totalProfit) {
        this.totalProfit = totalProfit;
    }

    public Integer getTotalProfit() {
        return totalProfit;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }


}