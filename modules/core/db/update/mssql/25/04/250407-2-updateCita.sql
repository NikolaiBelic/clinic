exec sp_rename 'CLINIC_CITA.HORA', 'HORA__U80217', 'COLUMN' ^
alter table CLINIC_CITA alter column HORA__U80217 datetime2  ;
alter table CLINIC_CITA add HORA_FINAL datetime2 ^
update CLINIC_CITA set HORA_FINAL = current_timestamp where HORA_FINAL is null ;
alter table CLINIC_CITA alter column HORA_FINAL datetime2 not null ;
alter table CLINIC_CITA add HORA_INICIO datetime2 ^
update CLINIC_CITA set HORA_INICIO = current_timestamp where HORA_INICIO is null ;
alter table CLINIC_CITA alter column HORA_INICIO datetime2 not null ;
