package com.company.salescafe.web.product;

import com.haulmont.cuba.gui.components.AbstractEditor;
import com.company.salescafe.entity.Product;

public class ProductEdit extends AbstractEditor<Product> {

    @Override
    protected void initNewItem(Product item) {
        super.initNewItem(item);
        item.setIsAvailable(true);
    }
}