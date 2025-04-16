create table CLINIC_DATOS_FACTURACION (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NIF varchar(9),
    NOMBRE varchar(50),
    APELLIDOS varchar(100),
    CALLE varchar(150),
    NUMERO varchar(50),
    CIUDAD varchar(50),
    PROVINCIA varchar(50),
    --
    primary key nonclustered (ID)
);