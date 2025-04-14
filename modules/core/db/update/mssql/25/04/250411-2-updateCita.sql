alter table CLINIC_CITA add DIA datetime2 ^
update CLINIC_CITA set DIA = convert (date, current_timestamp) where DIA is null ;
alter table CLINIC_CITA alter column DIA datetime2 not null ;
