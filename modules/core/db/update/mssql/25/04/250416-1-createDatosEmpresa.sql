create table CLINIC_DATOS_EMPRESA (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NOMBRE varchar(150) not null,
    DIRECCION varchar(255),
    NIF varchar(9),
    TELEFONO varchar(9),
    EMAIL varchar(75),
    --
    primary key nonclustered (ID)
);