create table CLINIC_EXPEDIENTE (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    TITULO varchar(75),
    DESCRIPCION varchar(max),
    ESPECIALISTA_ID uniqueidentifier,
    PACIENTE_ID uniqueidentifier,
    --
    primary key nonclustered (ID)
);