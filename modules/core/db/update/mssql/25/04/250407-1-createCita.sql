create table CLINIC_CITA (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    HORA datetime2 not null,
    PACIENTE_ID uniqueidentifier,
    ESPECIALISTA_ID uniqueidentifier,
    SERVICIO_ID uniqueidentifier,
    PAGADO tinyint not null,
    --
    primary key nonclustered (ID)
);