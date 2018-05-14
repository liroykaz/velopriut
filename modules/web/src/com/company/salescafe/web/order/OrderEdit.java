package com.company.salescafe.web.order;

import com.company.salescafe.entity.*;
import com.company.salescafe.services.OrderService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.data.CollectionDatasource;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

public class OrderEdit extends AbstractEditor<Order> {

    @Named("fieldGroupRight.timeOfOrder")
    protected Field timeOfOrder;
    @Named("fieldGroupLeft.orderStatus")
    protected Field orderStatus;
    @Named("fieldGroupRight.allCost")
    protected Field allCost;

    @Inject
    protected Table orderCardTable;

    @Inject
    protected CollectionDatasource<OrderCard, UUID> orderCardDs;
    @Inject
    protected Datasource<Order> orderDs;
    @Inject
    protected OrderService orderService;
    @Inject
    private ComponentsFactory componentsFactory;
    @Inject
    private Messages messages;

    @Override
    protected void postInit() {
        super.postInit();
        initOrderProperties();
        initOrderCardTableStyleProvider();
        showNotificationIfCloseOrderNotCompleted();

        orderCardDs.addCollectionChangeListener(e -> generateTotalCost(e.getItems()));
        getDsContext().setDiscardCommitted(false);
    }

    @Override
    protected void initNewItem(Order item) {
        super.initNewItem(item);

        if (getContext().getParams().get("workDay") != null) {
            WorkDay workDay = (WorkDay) getContext().getParams().get("workDay");
            item.setNumberOfOrder(orderService.generateNewOrderNumber(workDay.getId()));
            item.setWorkDay(workDay);
        }
        item.setOrderStatus(OrderStatus.isaccepted);
        item.setTimeOfOrder(new Date());
    }

    protected void generateTotalCost(List<OrderCard> cardList) {
        int totalCost = 0;
        cardList = getItem().getOrderCard();

        for (OrderCard orderCard : cardList) {
            totalCost += orderCard.getPrice();
        }
        getItem().setAllCost(totalCost);
    }

    protected void initOrderProperties() {
        if (getItem() != null && getItem().getOrderCard() != null)
            generateTotalCost(getItem().getOrderCard());
        else
            allCost.setValue(0);
    }

    public Component generateActionsCell(OrderCard entity) {
        HBoxLayout hbox = componentsFactory.createComponent(HBoxLayout.class);
        hbox.setSpacing(true);

        final Button passButton = componentsFactory.createComponent(Button.class);
        passButton.setAction(new AbstractAction("orderPassed") {
            @Override
            public void actionPerform(Component component) {
                entity.setProductStatus(ProductStatus.isComplete);
                orderCardTable.repaint();
            }
        });
        passButton.setCaption("Выполнен");
        passButton.setStyleName("friendly");
        passButton.setVisible(ProductStatus.inWork.equals(entity.getProductStatus()));

        final Button acceptButton = componentsFactory.createComponent(Button.class);
        acceptButton.setAction(new AbstractAction("acceptOrder") {
            @Override
            public void actionPerform(Component component) {
                entity.setProductStatus(ProductStatus.inWork);
                orderCardTable.repaint();
            }
        });
        acceptButton.setCaption("Принять в работу");
        acceptButton.setStyleName("primary");
        acceptButton.setVisible(ProductStatus.isAccepted.equals(entity.getProductStatus()));

        final Button reopenButton = componentsFactory.createComponent(Button.class);
        reopenButton.setAction(new AbstractAction("reopenOrder") {
            @Override
            public void actionPerform(Component component) {
                entity.setProductStatus(ProductStatus.isAccepted);
                orderCardTable.repaint();
            }
        });
        reopenButton.setCaption("Выполнить заного");
        reopenButton.setStyleName("danger");
        reopenButton.setVisible(ProductStatus.isComplete.equals(entity.getProductStatus()));

        hbox.add(passButton);
        hbox.add(acceptButton);
        hbox.add(reopenButton);
        return hbox;
    }

    protected void initOrderCardTableStyleProvider() {
        orderCardTable.setStyleProvider((entity, property) -> {
            if (StringUtils.isNotEmpty(property) && "productStatus".equals(property)) {
                if (ProductStatus.isComplete.equals(((OrderCard) entity).getProductStatus()))
                    return "isComplete";
                if (ProductStatus.inWork.equals(((OrderCard) entity).getProductStatus()))
                    return "inWork";
                if (ProductStatus.isAccepted.equals(((OrderCard) entity).getProductStatus()))
                    return "isAccepted";
            }

            return null;
        });
    }

    protected void showNotificationIfCloseOrderNotCompleted() {
        orderStatus.addValueChangeListener(e -> {
            if (OrderStatus.isCompleted.equals(e.getValue())) {
                int countNotCompletedCards = (int) getItem().getOrderCard().stream().filter(o -> !ProductStatus.isComplete.equals(o.getProductStatus())).count();
                if (countNotCompletedCards > 0) {
                    showNotification(getMessage("notCompletedCardsError"), NotificationType.HUMANIZED);
                    getItem().setOrderStatus((OrderStatus) e.getPrevValue());
                }
            }
        });
    }
}