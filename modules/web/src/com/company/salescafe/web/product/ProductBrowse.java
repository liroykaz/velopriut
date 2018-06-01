package com.company.salescafe.web.product;

import com.company.salescafe.entity.Product;
import com.haulmont.cuba.gui.components.AbstractLookup;
import com.haulmont.cuba.gui.data.GroupDatasource;

import javax.inject.Inject;
import java.util.Map;
import java.util.UUID;

public class ProductBrowse extends AbstractLookup {
    @Inject
    GroupDatasource<Product, UUID> productsDs;

    @Override
    public void init(Map<String, Object> params) {
        super.init(params);
        productsDs.addItemPropertyChangeListener(e->{
            if("isAvailable".equals(e.getProperty()))
                productsDs.commit();
        });
    }
}