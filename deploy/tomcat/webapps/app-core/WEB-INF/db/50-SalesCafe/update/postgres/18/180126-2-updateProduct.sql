alter table SALESCAFE_PRODUCT add column IS_NOT_AVAILABLE boolean ;
alter table SALESCAFE_PRODUCT drop column IS_AVAILABLE cascade ;
