CREATE TABLE PASSENGER
(
    PASSENGER_ID                    VARCHAR2(40)            PRIMARY KEY,
    FIRST_NAME                      VARCHAR2(40)            NOT NULL,
    LAST_NAME                       VARCHAR2(40)            NOT NULL,
    GENDER                          CHAR(1),
    DATE_OF_BIRTH                   VARCHAR2(10),
    NATIONALITY                     VARCHAR2(2),
    DOCUMENT_TYPE                   VARCHAR2(20),
    DOCUMENT_NUMBER                 VARCHAR2(40),
    DOCUMENT_COUNTRY                VARCHAR2(40),
    DOCUMENT_ISSUE_DATE             VARCHAR2(10),
    DOCUMENT_EXPIRATION_DATE        VARCHAR2(10),
    SEAT_PREFERENCE                 VARCHAR2(6)
);
