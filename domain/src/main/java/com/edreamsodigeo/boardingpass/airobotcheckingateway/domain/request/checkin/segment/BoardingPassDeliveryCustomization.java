package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment;

public class BoardingPassDeliveryCustomization {

    private final String brand;
    private final String language;
    private final String country;
    private final String bookingEmail;
    private final String deliveryEmail;

    public BoardingPassDeliveryCustomization(String brand, String language, String country, String bookingEmail, String deliveryEmail) {
        this.brand = brand;
        this.language = language;
        this.country = country;
        this.bookingEmail = bookingEmail;
        this.deliveryEmail = deliveryEmail;
    }

    public String brand() {
        return brand;
    }

    public String language() {
        return language;
    }

    public String country() {
        return country;
    }

    public String bookingEmail() {
        return bookingEmail;
    }

    public String deliveryEmail() {
        return deliveryEmail;
    }
}
