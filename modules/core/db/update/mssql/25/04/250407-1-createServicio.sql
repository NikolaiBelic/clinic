create table CLINIC_SERVICIO (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    DESCRIPCION varchar(255) not null,
    ESPECIALIDAD varchar(50),
    PRECIO double precision,
    --
    primary key nonclustered (ID)
);