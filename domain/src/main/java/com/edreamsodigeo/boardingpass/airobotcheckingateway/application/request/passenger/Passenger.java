package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

public class Passenger {
    private final PassengerId id;
    private final String name;
    private final String surname;
    private final String gender;
    private final String dateOfBirth;
    private final String nationality;
    private final Document document;
    private final SeatPreference seatPreference;

    public Passenger(PassengerId id, String name, String surname, String gender, String dateOfBirth, String nationality, Document document, SeatPreference seatPreference) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.document = document;
        this.seatPreference = seatPreference;
    }

    public PassengerId id() {
        return id;
    }

    public String name() {
        return name;
    }

    public String surname() {
        return surname;
    }

    public String gender() {
        return gender;
    }

    public String dateOfBirth() {
        return dateOfBirth;
    }

    public Document document() {
        return document;
    }

    public SeatPreference seatPreference() {
        return seatPreference;
    }

    public String nationality() {
        return nationality;
    }
}
