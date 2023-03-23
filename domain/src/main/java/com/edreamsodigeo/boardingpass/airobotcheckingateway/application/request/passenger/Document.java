package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;

public class Document {

    private final DocumentType type;
    private final String number;
    private final String expireDate;
    private final String issueDate;
    private final String country;

    public Document(DocumentType type, String number, String expireDate, String issueDate, String country) {
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

    public String expireDate() {
        return expireDate;
    }

    public String issueDate() {
        return issueDate;
    }

    public String country() {
        return country;
    }
}

