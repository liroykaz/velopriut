package com.company.salescafe.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import com.haulmont.chile.core.annotations.Composition;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.chile.core.annotations.NamePattern;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NamePattern("%s %s %s %s|numberOfOrder,nameOfCustomer,timeOfOrder,comment")
@Table(name = "SALESCAFE_ORDER")
@Entity(name = "salescafe$Order")
public class Order extends StandardEntity {
    private static final long serialVersionUID = 1809157886638959799L;

    @Column(name = "NUMBER_OF_ORDER")
    protected Integer numberOfOrder;

    @Column(name = "ORDER_STATUS")
    protected Integer orderStatus;

    @Column(name = "NAME_OF_CUSTOMER")
    protected String nameOfCustomer;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TIME_OF_ORDER")
    protected Date timeOfOrder;

    @Column(name = "ALL_COST")
    protected Integer allCost;

    @Column(name = "COMMENT_")
    protected String comment;

    @Composition
    @OnDelete(DeletePolicy.CASCADE)
    @OneToMany(mappedBy = "order")
    protected List<OrderCard> orderCard;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_DAY_ID")
    protected WorkDay workDay;

    @Column(name = "TYPE_OF_CUSTOMER")
    protected Integer typeOfCustomer;

    public void setTypeOfCustomer(TypeOfCustomer typeOfCustomer) {
        this.typeOfCustomer = typeOfCustomer == null ? null : typeOfCustomer.getId();
    }

    public TypeOfCustomer getTypeOfCustomer() {
        return typeOfCustomer == null ? null : TypeOfCustomer.fromId(typeOfCustomer);
    }


    public void setWorkDay(WorkDay workDay) {
        this.workDay = workDay;
    }

    public WorkDay getWorkDay() {
        return workDay;
    }


    public Integer getNumberOfOrder() {
        return numberOfOrder;
    }

    public void setNumberOfOrder(Integer numberOfOrder) {
        this.numberOfOrder = numberOfOrder;
    }



    public OrderStatus getOrderStatus() {
        return orderStatus == null ? null : OrderStatus.fromId(orderStatus);
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus == null ? null : orderStatus.getId();
    }



    public void setNameOfCustomer(String nameOfCustomer) {
        this.nameOfCustomer = nameOfCustomer;
    }

    public String getNameOfCustomer() {
        return nameOfCustomer;
    }

    public void setTimeOfOrder(Date timeOfOrder) {
        this.timeOfOrder = timeOfOrder;
    }

    public Date getTimeOfOrder() {
        return timeOfOrder;
    }

    public void setAllCost(Integer allCost) {
        this.allCost = allCost;
    }

    public Integer getAllCost() {
        return allCost;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setOrderCard(List<OrderCard> orderCard) {
        this.orderCard = orderCard;
    }

    public List<OrderCard> getOrderCard() {
        return orderCard;
    }


}