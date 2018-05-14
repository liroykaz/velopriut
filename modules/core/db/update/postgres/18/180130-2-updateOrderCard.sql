alter table SALESCAFE_ORDER_CARD drop column PRODUCT_STATUS cascade ;
alter table SALESCAFE_ORDER_CARD add column PRODUCT_STATUS varchar(50) ;
