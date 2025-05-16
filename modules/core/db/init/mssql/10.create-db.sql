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
    FECHA_NACIMIENTO datetime2,
    GENERO varchar(50) not null,
    --
    primary key nonclustered (ID)
)^
-- end CLINIC_PACIENTE
-- begin CLINIC_DATOS_EMPRESA
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
)^
-- end CLINIC_DATOS_EMPRESA
-- begin CLINIC_DATOS_ADMINISTRATIVOS
create table CLINIC_DATOS_ADMINISTRATIVOS (
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
    ESTADO_PACIENTE varchar(50),
    CIUDAD_NACIMIENTO varchar(50),
    NACIONALIDAD varchar(50),
    PROVINCIA_NACIMIENTO varchar(50),
    TIPO_DOCUMENTO varchar(50),
    NUMERO_DOCUMENTO varchar(9),
    --
    primary key nonclustered (ID)
)^
-- end CLINIC_DATOS_ADMINISTRATIVOS
-- begin CLINIC_DATOS_FACTURACION
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
    PACIENTE_ID uniqueidentifier,
    NIF varchar(9),
    NOMBRE varchar(50),
    APELLIDOS varchar(100),
    CALLE varchar(150),
    NUMERO varchar(50),
    CIUDAD varchar(50),
    PROVINCIA varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end CLINIC_DATOS_FACTURACION
-- begin CLINIC_DATOS_CONTACTO
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
    PROVINCIA varchar(255),
    --
    primary key nonclustered (ID)
)^
-- end CLINIC_DATOS_CONTACTO
