create table CLINIC_ESPECIALISTA (
    ID uniqueidentifier,
    VERSION integer not null,
    CREATE_TS datetime2,
    CREATED_BY varchar(50),
    UPDATE_TS datetime2,
    UPDATED_BY varchar(50),
    DELETE_TS datetime2,
    DELETED_BY varchar(50),
    --
    NOMBRE varchar(30),
    APELLIDOS varchar(50),
    DNI varchar(9) not null,
    ESPECIALIDAD varchar(50) not null,
    --
    primary key nonclustered (ID)
);