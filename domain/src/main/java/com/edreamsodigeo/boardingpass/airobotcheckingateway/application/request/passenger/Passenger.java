package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.validation.Checker;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Objects;

public class Passenger {

    private final PassengerId id;
    private final ProviderPassengerId providerPassengerId;
    @NotBlank
    private final String name;
    @NotBlank
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final Gender gender;
    private final String nationality;
    private final Document document;

    private Passenger(PassengerId id, ProviderPassengerId providerPassengerId, String name, String lastName, LocalDate dateOfBirth, Gender gender, String nationality, Document document) {
        this.id = id;
        this.providerPassengerId = providerPassengerId;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.nationality = nationality;
        this.document = document;
    }

    public PassengerId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String lastName() {
        return lastName;
    }

    public LocalDate dateOfBirth() {
        return dateOfBirth;
    }

    public ProviderPassengerId providerPassengerId() {
        return providerPassengerId;
    }

    public Gender gender() {
        return gender;
    }

    public String nationality() {
        return nationality;
    }

    public Document document() {
        return document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Passenger passenger = (Passenger) o;
        return id.equals(passenger.id);
    }

    public static Passenger.PassengerBuilder builder() {
        return new Passenger.PassengerBuilder();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public static class PassengerBuilder {

        private PassengerId id;
        private ProviderPassengerId providerPassengerId;
        private String name;
        private String lastName;
        private LocalDate dateOfBirth;
        private Gender gender;
        private String nationality;
        private Document document;

        public PassengerBuilder withId(PassengerId id) {
            this.id = id;
            return this;
        }

        public PassengerBuilder withProviderPassengerId(ProviderPassengerId providerPassengerId) {
            this.providerPassengerId = providerPassengerId;
            return this;
        }

        public PassengerBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public PassengerBuilder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public PassengerBuilder withDateOfBirth(LocalDate dateOfBirth) {
            this.dateOfBirth = dateOfBirth;
            return this;
        }

        public PassengerBuilder withGender(Gender gender) {
            this.gender = gender;
            return this;
        }

        public PassengerBuilder withNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public PassengerBuilder withDocument(Document document) {
            this.document = document;
            return this;
        }

        public Passenger build() {
            Passenger passenger = new Passenger(this.id, this.providerPassengerId, this.name, this.lastName, this.dateOfBirth,
                    this.gender, this.nationality, this.document);
            Checker.checkValidityWithJSR380(passenger);
            return passenger;
        }

    }
}
