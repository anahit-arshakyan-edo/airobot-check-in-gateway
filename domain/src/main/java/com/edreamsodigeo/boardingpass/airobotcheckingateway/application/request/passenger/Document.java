package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.validation.Checker;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

public final class Document {
    @NotNull
    private final DocumentType type;
    @NotBlank
    private final String number;
    @NotNull
    private final LocalDate expireDate;
    private final LocalDate issueDate;
    @NotBlank
    private final String country;

    private Document(DocumentType type, String number, LocalDate expireDate, LocalDate issueDate, String country) {
        this.type = type;
        this.number = number;
        this.expireDate = expireDate;
        this.issueDate = issueDate;
        this.country = country;
    }

    public static Document create(DocumentType type, String number, LocalDate expireDate, LocalDate issueDate, String country) {
        Document document = new Document(type, number, expireDate, issueDate, country);
        Checker.checkValidityWithJSR380(document);
        return document;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Document document = (Document) o;
        return type == document.type && number.equals(document.number) && expireDate.equals(document.expireDate) && country.equals(document.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, number, expireDate, issueDate, country);
    }
}
