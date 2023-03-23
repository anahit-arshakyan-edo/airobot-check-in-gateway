CREATE TABLE BOARDING_PASS_DELIVERY
(
    DELIVERY_REQUEST_ID              VARCHAR2(40)               PRIMARY KEY,
    BRAND                            VARCHAR2(20)               NOT NULL,
    LANG                             VARCHAR2(2)                NOT NULL,
    COUNTRY                          VARCHAR2(5)                NOT NULL,
    BOOKING_DATE                     TIMESTAMP,
    BOOKING_EMAIL                    VARCHAR2(60)               NOT NULL,
    DELIVERY_EMAIL                   VARCHAR2(60)               NOT NULL
);
