UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.3.0' where ID ='FF-1628/00_alter_table_boarding_pass.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.3.0' where ID ='FF-1628/01_drop_table_section.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.3.0' where ID ='FF-1628/02_drop_table_passenger.ddl.sql';
INSERT INTO GE_RELEASE_LOG (ID,EXECUTION_DATE,MODULE) values('1.3.0',systimestamp,'airobot-check-in-gateway');
