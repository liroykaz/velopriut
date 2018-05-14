update SALESCAFE_ORDER_CARD set PRODUCT_TYPE = 10 where PRODUCT_TYPE is null ;
alter table SALESCAFE_ORDER_CARD alter column PRODUCT_TYPE set not null ;
update SALESCAFE_ORDER_CARD set PRICE = 0 where PRICE is null ;
alter table SALESCAFE_ORDER_CARD alter column PRICE set not null ;
update SALESCAFE_ORDER_CARD set AMOUNT = 0 where AMOUNT is null ;
alter table SALESCAFE_ORDER_CARD alter column AMOUNT set not null ;
