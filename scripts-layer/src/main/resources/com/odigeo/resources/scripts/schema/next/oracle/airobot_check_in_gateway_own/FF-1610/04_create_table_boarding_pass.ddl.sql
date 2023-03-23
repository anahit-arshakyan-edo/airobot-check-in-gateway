CREATE TABLE BOARDING_PASS
(
    DELIVERY_ID        VARCHAR2(40)         REFERENCES BOARDING_PASS_DELIVERY (DELIVERY_REQUEST_ID),
    JOURNEY_ID         VARCHAR2(40)         REFERENCES JOURNEY (JOURNEY_ID),
    PASSENGER_ID       VARCHAR2(40)         REFERENCES PASSENGER (PASSENGER_ID),
    STATUS             VARCHAR2(10)         NOT NULL,
    URL                VARCHAR2(2000),
    FORMAT             VARCHAR2(10),
    CONSTRAINT PK_BOARDING_PASS PRIMARY KEY (DELIVERY_ID, JOURNEY_ID, PASSENGER_ID)
);
