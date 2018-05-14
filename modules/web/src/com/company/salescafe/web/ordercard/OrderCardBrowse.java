package com.company.salescafe.web.ordercard;

import com.company.salescafe.entity.OrderCard;
import com.company.salescafe.services.OrderService;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.components.Button;
import com.haulmont.cuba.gui.components.DateField;
import com.haulmont.cuba.gui.components.GroupTable;
import com.haulmont.cuba.gui.data.GroupDatasource;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderCardBrowse extends AbstractLookup {

    @Inject
    private Button applyButton;

    @Inject
    protected DateField dateFirstField;
    @Inject
    protected DateField dateSecondField;

    @Inject
    private OrderService orderService;

    @Inject
    protected GroupDatasource<OrderCard, UUID> orderCardsDs;

    @Inject
    private GroupTable<OrderCard> orderCardsTable;

    public void onApplyButtonClick() {
        if (dateFirstField.getValue() != null && dateSecondField.getValue() != null) {
            List<OrderCard> orderCards = orderCardsDs.getItems().stream().collect(Collectors.toList());
            orderCards.forEach(e -> orderCardsDs.excludeItem(e));
            orderCardsTable.setVisible(true);
            orderService.getFilteredBetweenDateList(dateFirstField.getValue(), dateSecondField.getValue())
                    .forEach(e -> orderCardsDs.addItem(e));
        } else
            showNotification(getMessage("errorFilteringApply"), NotificationType.HUMANIZED);
    }
}