package com.company.salescafe.services;

import com.company.salescafe.entity.Order;
import com.company.salescafe.entity.OrderCard;
import com.company.salescafe.entity.OrderStatus;
import com.company.salescafe.entity.Product;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    String NAME = "sales_OrderService";

    int generateNewOrderNumber(UUID workDayId);

    void setOrderStatus(Order order, OrderStatus status);

    List<OrderCard> getFilteredBetweenDateList(Date startDate, Date endDate);

    void changeResidueAfterPurchase(Product product);
}
