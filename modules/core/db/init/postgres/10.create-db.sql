-- begin SALESCAFE_ORDER_CARD
create table SALESCAFE_ORDER_CARD (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    AMOUNT integer not null,
    PRODUCT_ID uuid,
    PRODUCT_TYPE integer not null,
    PRODUCT_STATUS varchar(50),
    ORDER_ID uuid,
    PRICE integer not null,
    --
    primary key (ID)
)^
-- end SALESCAFE_ORDER_CARD
-- begin SALESCAFE_ORDER
create table SALESCAFE_ORDER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    NUMBER_OF_ORDER integer,
    ORDER_STATUS integer,
    NAME_OF_CUSTOMER varchar(255),
    TIME_OF_ORDER timestamp,
    ALL_COST integer,
    COMMENT_ varchar(255),
    WORK_DAY_ID uuid,
    TYPE_OF_CUSTOMER integer,
    --
    primary key (ID)
)^
-- end SALESCAFE_ORDER
-- begin SALESCAFE_PRODUCT
create table SALESCAFE_PRODUCT (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    PRODUCT_NAME varchar(255),
    WEIGHT integer,
    PRODUCT_PRICE integer,
    IS_AVAILABLE boolean,
    RESIDUE integer,
    PRODUCT_TYPE integer,
    --
    primary key (ID)
)^
-- end SALESCAFE_PRODUCT
-- begin SALESCAFE_WORK_DAY
create table SALESCAFE_WORK_DAY (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TOTAL_PROFIT integer,
    DAY_OF_WEEK integer,
    WORK_DATE date,
    --
    primary key (ID)
)^
-- end SALESCAFE_WORK_DAY
