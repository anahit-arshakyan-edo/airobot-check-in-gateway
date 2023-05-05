package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.validation.Checker;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public final class BoardingPassDeliveryCustomization {

    @NotBlank
    private final String brand;
    @NotBlank
    private final String language;
    @NotBlank
    private final String country;
    @NotBlank
    private final String bookingEmail;
    @NotBlank
    private final String deliveryEmail;

    private BoardingPassDeliveryCustomization(String brand, String language, String country, String bookingEmail, String deliveryEmail) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardingPassDeliveryCustomization that = (BoardingPassDeliveryCustomization) o;
        return brand.equals(that.brand) && language.equals(that.language) && country.equals(that.country) && bookingEmail.equals(that.bookingEmail) && deliveryEmail.equals(that.deliveryEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, language, country, bookingEmail, deliveryEmail);
    }

    public static BoardingPassDeliveryCustomization.BoardingPassDeliveryCustomizationBuilder builder() {
        return new BoardingPassDeliveryCustomization.BoardingPassDeliveryCustomizationBuilder();
    }

    public static class BoardingPassDeliveryCustomizationBuilder {

        private String brand;
        private String language;
        private String country;
        private String bookingEmail;
        private String deliveryEmail;

        public BoardingPassDeliveryCustomizationBuilder withBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public BoardingPassDeliveryCustomizationBuilder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public BoardingPassDeliveryCustomizationBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public BoardingPassDeliveryCustomizationBuilder withBookingEmail(String bookingEmail) {
            this.bookingEmail = bookingEmail;
            return this;
        }

        public BoardingPassDeliveryCustomizationBuilder withDeliveryEmail(String deliveryEmail) {
            this.deliveryEmail = deliveryEmail;
            return this;
        }

        public BoardingPassDeliveryCustomization build() {
            BoardingPassDeliveryCustomization boardingPassDeliveryCustomization =
                    new BoardingPassDeliveryCustomization(this.brand, this.language, this.country, this.bookingEmail, this.deliveryEmail);
            Checker.checkValidityWithJSR380(boardingPassDeliveryCustomization);
            return boardingPassDeliveryCustomization;
        }
    }


}
