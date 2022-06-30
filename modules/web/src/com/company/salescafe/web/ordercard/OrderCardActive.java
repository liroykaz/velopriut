

package com.company.salescafe.web.ordercard;

import com.company.salescafe.entity.*;
import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.WindowManager;
import com.haulmont.cuba.gui.components.*;
import com.haulmont.cuba.gui.components.actions.EditAction;
import com.haulmont.cuba.gui.data.GroupDatasource;
import com.haulmont.cuba.gui.xml.layout.ComponentsFactory;
import org.apache.commons.lang3.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.UUID;

public class OrderCardActive extends AbstractLookup {

    @Named("orderCardsTable.edit")
    private EditAction orderCardsTableEditAction;

    @Inject
    private LookupPickerField workDaylookupPickerField;

    @Inject
    private GroupTable<OrderCard> orderCardsTable;

    @Inject
    private GroupDatasource<OrderCard, UUID> orderCardsDs;

    @Inject
    private ComponentsFactory componentsFactory;

    @Inject
    protected Messages messages;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        initOrderCardTableStyleProvider();

        orderCardsTable.setItemClickAction(new AbstractAction("viewOrder") {
            @Override
            public void actionPerform(Component component) {
                openOrderFrameForSelectedCard();
            }
        });
    }

    protected void initOrderCardTableStyleProvider() {
        orderCardsTable.setStyleProvider((entity, property) -> {
            if (StringUtils.isNotEmpty(property) && "productStatus".equals(property)) {
                if (ProductStatus.isComplete.equals((entity.getProductStatus())))
                    return "isComplete";
                if (ProductStatus.isAccepted.equals(entity.getProductStatus()))
                    return "isAccepted";
            }

            return null;
        });
    }

    public void onApplyButtonClick() {
        if (workDaylookupPickerField.getValue() != null) {
            setFilterByWorkDayAtDatasource(((WorkDay) workDaylookupPickerField.getValue()).getId());
        } else
            showNotification(getMessage("emptyWorkDayFiledError"));
    }

    public void onEditBtnClick() {
        openOrderFrameForSelectedCard();
    }

    public Component generateActionsCell(OrderCard entity) {
        HBoxLayout hbox = componentsFactory.createComponent(HBoxLayout.class);
        hbox.setSpacing(true);

        final Button passButton = componentsFactory.createComponent(Button.class);
        passButton.setAction(new AbstractAction("orderPassed") {
            @Override
            public void actionPerform(Component component) {
                entity.setProductStatus(ProductStatus.isComplete);
                orderCardsDs.commit();
                orderCardsTable.repaint();
            }
        });
        passButton.setCaption("Выполнен");
        passButton.setStyleName("friendly");
        passButton.setVisible(ProductStatus.isAccepted.equals(entity.getProductStatus()));

        final Button reopenButton = componentsFactory.createComponent(Button.class);
        reopenButton.setAction(new AbstractAction("reopenOrder") {
            @Override
            public void actionPerform(Component component) {
                entity.setProductStatus(ProductStatus.isAccepted);
                orderCardsDs.commit();
                orderCardsTable.repaint();
            }
        });
        reopenButton.setCaption("Выполнить заного");
        reopenButton.setStyleName("danger");
        reopenButton.setVisible(ProductStatus.isComplete.equals(entity.getProductStatus()));

        hbox.add(passButton);
        hbox.add(reopenButton);
        return hbox;
    }

    protected void openOrderFrameForSelectedCard() {
        openEditor("salescafe$Order.edit", orderCardsTable.getSingleSelected().getOrder(), WindowManager.OpenType.NEW_WINDOW)
                .addCloseListener(new CloseListener() {
                    @Override
                    public void windowClosed(String actionId) {
                        orderCardsDs.refresh();
                    }
                });
    }

    protected void setFilterByWorkDayAtDatasource(UUID workDayId) {
        orderCardsDs.setQuery("select e from salescafe$OrderCard e where e.order.workDay.id = '" + workDayId.toString() + "' order by e.order.numberOfOrder desc");
        orderCardsDs.refresh();
        orderCardsTable.repaint();
    }

    public Component generateNumberOfOrderCell(OrderCard entity) {
        final Label numberOfOrderLabel = componentsFactory.createComponent(Label.class);
        StringBuilder sb = new StringBuilder();
        String orderStatus = getCaptionOfStatus(entity.getOrder().getOrderStatus());
        sb.append(entity.getOrder().getNumberOfOrder()).append(orderStatus);
        numberOfOrderLabel.setValue(sb.toString());
		return numberOfOrderLabel;
    }

    protected String getCaptionOfStatus(OrderStatus orderStatus) {
        if (OrderStatus.isaccepted.equals(orderStatus))
            return "(Принят)";

        return "";
    }
}