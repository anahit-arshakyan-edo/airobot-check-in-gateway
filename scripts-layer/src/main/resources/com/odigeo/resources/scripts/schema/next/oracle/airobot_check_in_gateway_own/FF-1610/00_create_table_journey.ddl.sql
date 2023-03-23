CREATE TABLE JOURNEY
(
    JOURNEY_ID                  VARCHAR2(40)            PRIMARY KEY,
    AIRLINE                     VARCHAR2(2)             NOT NULL,
    DEPARTURE_AIRPORT           VARCHAR2(3)             NOT NULL,
    ARRIVAL_AIRPORT             VARCHAR2(3)             NOT NULL,
    DEPARTURE_DATE              TIMESTAMP               NOT NULL,
    ARRIVAL_DATE                TIMESTAMP               NOT NULL,
    FLIGHT_NUMBER               NUMBER(6, 0)            NOT NULL,
    BOOKING_ID                  NUMBER(38, 0)           NOT NULL,
    PNR                         VARCHAR2(6)             NOT NULL,
    TICKET_NUMBER               VARCHAR2(13)
);
