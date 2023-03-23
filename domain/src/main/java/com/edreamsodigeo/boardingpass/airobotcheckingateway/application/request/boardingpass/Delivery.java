package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import java.time.LocalDateTime;

public class Delivery {

    private final DeliveryId id;
    private final String brand;
    private final String lang;
    private final String country;
    private final LocalDateTime bookingDate;
    private final String bookingEmail;
    private final String boardingPassDeliveryEmail;

    public Delivery(DeliveryId id, String brand, String lang, String country, LocalDateTime bookingDate, String bookingEmail, String boardingPassDeliveryEmail) {
        this.id = id;
        this.brand = brand;
        this.lang = lang;
        this.country = country;
        this.bookingDate = bookingDate;
        this.bookingEmail = bookingEmail;
        this.boardingPassDeliveryEmail = boardingPassDeliveryEmail;
    }

    public DeliveryId id() {
        return id;
    }

    public String brand() {
        return brand;
    }

    public String lang() {
        return lang;
    }

    public String country() {
        return country;
    }

    public LocalDateTime bookingDate() {
        return bookingDate;
    }

    public String bookingEmail() {
        return bookingEmail;
    }

    public String boardingPassDeliveryEmail() {
        return boardingPassDeliveryEmail;
    }
}
