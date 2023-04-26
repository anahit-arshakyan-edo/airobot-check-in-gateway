UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='FF-1610/00_create_table_check_in.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='FF-1610/01_create_table_check_in_request.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='FF-1610/02_create_table_passenger.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='FF-1610/03_create_table_section.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='FF-1610/04_create_table_boarding_pass.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='rollback/R00_create_table_boarding_pass.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='rollback/R01_create_table_section.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='rollback/R02_create_table_passenger.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='rollback/R03_create_table_check_in_request.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.2.0' where ID ='rollback/R04_create_table_check_in.ddl.sql';
INSERT INTO GE_RELEASE_LOG (ID,EXECUTION_DATE,MODULE) values('1.2.0',systimestamp,'airobot-check-in-gateway');
