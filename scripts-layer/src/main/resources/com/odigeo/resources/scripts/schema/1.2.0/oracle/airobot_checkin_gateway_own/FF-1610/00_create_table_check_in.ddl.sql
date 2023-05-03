CREATE TABLE CHECK_IN
(
    ID           RAW(16) PRIMARY KEY,
    REFERENCE_ID NUMBER(20) NOT NULL,
    CREATED_AT   TIMESTAMP NOT NULL,
    CONSTRAINT UNIQUE_REFERENCE_ID UNIQUE (REFERENCE_ID)
);