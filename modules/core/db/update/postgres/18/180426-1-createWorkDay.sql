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
    WORK_DATE date,
    --
    primary key (ID)
);
