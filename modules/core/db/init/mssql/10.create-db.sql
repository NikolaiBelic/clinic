-- begin CLINIC_CITA
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
    DIA datetime2 not null,
    HORA_INICIO datetime2 not null,
    HORA_FINAL datetime2 not null,
    PACIENTE_ID uniqueidentifier,
    ESPECIALISTA_ID uniqueidentifier,
    SERVICIO_ID uniqueidentifier,
    PAGADO tinyint not null,
    --
    primary key nonclustered (ID)
)^
-- end CLINIC_CITA
-- begin CLINIC_SERVICIO
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
)^
-- end CLINIC_SERVICIO
-- begin CLINIC_EXPEDIENTE
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
)^
-- end CLINIC_EXPEDIENTE
-- begin CLINIC_ESPECIALISTA
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
)^
-- end CLINIC_ESPECIALISTA
-- begin CLINIC_PACIENTE
create table CLINIC_PACIENTE (
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
    EMAIL varchar(50),
    EDAD integer,
    DNI varchar(9) not null,
    FECHA_NACIMIENTO datetime2,
    DESCRIPCION varchar(max),
    GENERO varchar(50) not null,
    --
    primary key nonclustered (ID)
)^
-- end CLINIC_PACIENTE
