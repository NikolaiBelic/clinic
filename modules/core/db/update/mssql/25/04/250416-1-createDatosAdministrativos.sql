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
    RESPONSABLE_TRATAMIENTO_DATOS_ID uniqueidentifier,
    CIUDAD_NACIMIENTO varchar(50),
    NACIONALIDAD varchar(50),
    PROVINCIA_NACIMIENTO varchar(50),
    TIPO_DOCUMENTO varchar(50),
    NUMERO_DOCUMENTO varchar(9),
    --
    primary key nonclustered (ID)
);