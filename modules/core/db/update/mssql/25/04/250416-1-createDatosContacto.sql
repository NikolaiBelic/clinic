create table CLINIC_DATOS_CONTACTO (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    PACIENTE_ID uniqueidentifier,
    TELEFONO varchar(9),
    EMAIL varchar(60),
    CALLE varchar(150),
    NUMERO varchar(50),
    CODIGO_POSTAL varchar(5),
    CIUDAD varchar(30),
    PROVINCIA varchar(50),
    --
    primary key nonclustered (ID)
);