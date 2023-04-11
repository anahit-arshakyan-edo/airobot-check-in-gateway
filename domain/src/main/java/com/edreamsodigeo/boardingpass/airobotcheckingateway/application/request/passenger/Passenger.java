package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

import java.util.Date;
import java.util.Objects;

public class Passenger {

    private final PassengerId id;
    private final ProviderPassengerId providerPassengerId;
    private final String name;
    private final String lastName;
    private final Date dateOfBirth;

    public Passenger(PassengerId id, ProviderPassengerId providerPassengerId, String name, String lastName, Date dateOfBirth) {
        this.id = id;
        this.providerPassengerId = providerPassengerId;
        this.name = name;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
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

    public Date dateOfBirth() {
        return dateOfBirth;
    }

    public ProviderPassengerId providerPassengerId() {
        return providerPassengerId;
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
