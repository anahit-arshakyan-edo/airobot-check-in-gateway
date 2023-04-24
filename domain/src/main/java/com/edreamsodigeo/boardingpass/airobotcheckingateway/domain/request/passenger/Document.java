package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.DocumentType;

import java.time.LocalDate;

public class Document {
    private final DocumentType type;
    private final String number;
    private final LocalDate expireDate;
    private final LocalDate issueDate;
    private final String country;

    public Document(DocumentType type, String number, LocalDate expireDate, LocalDate issueDate, String country) {
        this.type = type;
        this.number = number;
        this.expireDate = expireDate;
        this.issueDate = issueDate;
        this.country = country;
    }

    public DocumentType type() {
        return type;
    }

    public String number() {
        return number;
    }

    public LocalDate expireDate() {
        return expireDate;
    }

    public LocalDate issueDate() {
        return issueDate;
    }

    public String country() {
        return country;
    }
}
