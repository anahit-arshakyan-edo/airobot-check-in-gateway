UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.10.0' where ID ='FF-1689/00_create_sequence_reference_id.ddl.sql';
UPDATE GE_SCRIPTS_LOG SET RELEASE_ID = '1.10.0' where ID ='rollback/R00_create_sequence_reference_id.ddl.sql';
INSERT INTO GE_RELEASE_LOG (ID,EXECUTION_DATE,MODULE) values('1.10.0',systimestamp,'airobot-check-in-gateway');
