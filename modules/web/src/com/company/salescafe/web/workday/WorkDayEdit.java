package com.company.salescafe.web.workday;

import com.company.salescafe.entity.*;
import com.company.salescafe.services.OrderService;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.CreateAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import org.apache.commons.lang.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorkDayEdit extends AbstractEditor<WorkDay> {

    @Named("ordersTable.create")
    private CreateAction orderCreateAction;

    @Inject
    private ComponentsFactory componentsFactory;

    @Inject
    protected Messages messages;
    @Inject
    protected GroupTable ordersTable;
    @Inject
    protected GroupDatasource<Order, UUID> ordersDs;

    @Inject
    protected OrderService orderService;

    @Override
    protected void postInit() {
        super.postInit();

        Map<String, Object> param = new HashMap<>();
        param.put("workDay", getItem());
        orderCreateAction.setWindowParams(param);

        orderCreateAction.setBeforeActionPerformedHandler(new Action.BeforeActionPerformedHandler() {
            @Override
            public boolean beforeActionPerformed() {
                commit();
                return true;
            }
        });

        ordersTable.setStyleProvider((entity, property) -> {
            if (StringUtils.isNotEmpty(property) && "orderStatus".equals(property)) {
                if (OrderStatus.isCompleted.equals(((Order) entity).getOrderStatus()))
                    return "isComplete";
                if (OrderStatus.isaccepted.equals(((Order) entity).getOrderStatus()))
                    return "isAccepted";
            }

            return null;
        });

        if (getItem() != null && getItem().getOrders() != null) {
            ordersDs.refresh();
            getItem().setTotalProfit(getItem().getOrders().stream().mapToInt(e -> e.getAllCost() != null ? e.getAllCost() : 0).sum());
        }
    }

    @Override
    protected void initNewItem(WorkDay item) {
        super.initNewItem(item);
        item.setWorkDate(new Date());
    }

    public Component generateOrderCardProductListCell(Order entity) {
        StringBuilder str = new StringBuilder();
        for (OrderCard orderCard : entity.getOrderCard()) {
            if (orderCard.getProduct() != null && orderCard.getProductStatus() != null && orderCard.getProductStatus().name() != null)
                str.append("[").append(messages.getMessage(ProductStatus.class, "ProductStatus." + orderCard.getProductStatus().name())).append("] ")
                        .append(orderCard.getProduct().getProductName())
                        .append(" x")
                        .append(orderCard.getAmount())
                        .append("\n");
            else
                str.append("[...]").append("\n");
        }
        Label proudctInfoLabel = componentsFactory.createComponent(Label.class);
        proudctInfoLabel.setValue(str.toString());
        return proudctInfoLabel;
    }

    public Component generateActionsCell(Order entity) {
        HBoxLayout hbox = componentsFactory.createComponent(HBoxLayout.class);
        hbox.setSpacing(true);

        final Button passButton = componentsFactory.createComponent(Button.class);
        passButton.setAction(new AbstractAction("orderPassed") {
            @Override
            public void actionPerform(Component component) {
                if (entity.getOrderCard() != null) {
                    int countNotCompletedCards = (int) entity.getOrderCard().stream().filter(o -> !ProductStatus.isComplete.equals(o.getProductStatus())).count();
                    if (countNotCompletedCards > 0) {
                        showNotification(getMessage("notCompletedCardsError"), NotificationType.HUMANIZED);
                    } else
                        orderService.setOrderStatus(entity, OrderStatus.isCompleted);
                    ordersDs.refresh();
                    ordersTable.repaint();
                }
            }
        });
        passButton.setCaption("Выполнен");
        passButton.setStyleName("friendly");
        passButton.setVisible(OrderStatus.isaccepted.equals(entity.getOrderStatus()));

//        final Button acceptButton = componentsFactory.createComponent(Button.class);
//        acceptButton.setAction(new AbstractAction("acceptOrder") {
//            @Override
//            public void actionPerform(Component component) {
//                orderService.setOrderStatus(entity, OrderStatus.inWork);
//                ordersDs.refresh();
//                ordersTable.repaint();
//            }
//        });
//        acceptButton.setCaption("Принять в работу");
//        acceptButton.setStyleName("primary");
//        acceptButton.setVisible(OrderStatus.isaccepted.equals(entity.getOrderStatus()));

        final Button reopenButton = componentsFactory.createComponent(Button.class);
        reopenButton.setAction(new AbstractAction("reopenOrder") {
            @Override
            public void actionPerform(Component component) {
                orderService.setOrderStatus(entity, OrderStatus.isaccepted);
                ordersDs.refresh();
                ordersTable.repaint();
            }
        });
        reopenButton.setCaption("Выполнить заного");
        reopenButton.setStyleName("danger");
        reopenButton.setVisible(OrderStatus.isCompleted.equals(entity.getOrderStatus()));

        hbox.add(passButton);
        //hbox.add(acceptButton);
        hbox.add(reopenButton);
        return hbox;
    }


}