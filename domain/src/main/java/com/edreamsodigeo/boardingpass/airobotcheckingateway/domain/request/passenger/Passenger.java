package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger;

import java.time.LocalDate;
import java.util.Objects;

public class Passenger {

    private final PassengerId id;
    private final ProviderPassengerId providerPassengerId;
    private final String name;
    private final String lastName;
    private final LocalDate dateOfBirth;
    private final Gender gender;
    private final String nationality;
    private final Document document;

    public Passenger(PassengerId id, ProviderPassengerId providerPassengerId, String name, String lastName, LocalDate dateOfBirth, Gender gender, String nationality, Document document) {
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
