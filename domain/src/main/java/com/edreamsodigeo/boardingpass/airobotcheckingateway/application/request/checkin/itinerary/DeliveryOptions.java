package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.validation.Checker;

import javax.validation.constraints.NotBlank;
import java.util.Objects;

public final class DeliveryOptions {

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

    private DeliveryOptions(String brand, String language, String country, String bookingEmail, String deliveryEmail) {
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
        DeliveryOptions that = (DeliveryOptions) o;
        return brand.equals(that.brand) && language.equals(that.language) && country.equals(that.country) && bookingEmail.equals(that.bookingEmail) && deliveryEmail.equals(that.deliveryEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, language, country, bookingEmail, deliveryEmail);
    }

    public static DeliveryOptionsBuilder builder() {
        return new DeliveryOptionsBuilder();
    }

    public static class DeliveryOptionsBuilder {

        private String brand;
        private String language;
        private String country;
        private String bookingEmail;
        private String deliveryEmail;

        public DeliveryOptionsBuilder withBrand(String brand) {
            this.brand = brand;
            return this;
        }

        public DeliveryOptionsBuilder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public DeliveryOptionsBuilder withCountry(String country) {
            this.country = country;
            return this;
        }

        public DeliveryOptionsBuilder withBookingEmail(String bookingEmail) {
            this.bookingEmail = bookingEmail;
            return this;
        }

        public DeliveryOptionsBuilder withDeliveryEmail(String deliveryEmail) {
            this.deliveryEmail = deliveryEmail;
            return this;
        }

        public DeliveryOptions build() {
            DeliveryOptions deliveryOptions =
                    new DeliveryOptions(this.brand, this.language, this.country, this.bookingEmail, this.deliveryEmail);
            Checker.checkValidityWithJSR380(deliveryOptions);
            return deliveryOptions;
        }
    }

}
