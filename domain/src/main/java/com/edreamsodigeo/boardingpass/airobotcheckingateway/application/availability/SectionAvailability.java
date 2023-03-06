package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.List;
import java.util.Objects;

public class SectionAvailability {
    private final Section section;
    private final CheckInWindow checkInWindow;
    private final List<PassengerRequirement> passengerRequirements;
    private final boolean requiresDocuments;
    private final List<Document> permittedDocuments;

    public SectionAvailability(Section section, CheckInWindow checkInWindow,
                               List<PassengerRequirement> passengerRequirements,
                               boolean requiresDocuments, List<Document> permittedDocuments) {
        this.section = section;
        this.checkInWindow = checkInWindow;
        this.passengerRequirements = passengerRequirements;
        this.requiresDocuments = requiresDocuments;
        this.permittedDocuments = permittedDocuments;
    }

    public Section section() {
        return section;
    }

    public CheckInWindow checkInWindow() {
        return checkInWindow;
    }

    public List<PassengerRequirement> passengerRequirements() {
        return passengerRequirements;
    }

    public boolean requiresDocuments() {
        return requiresDocuments;
    }

    public List<Document> permittedDocuments() {
        return permittedDocuments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SectionAvailability that = (SectionAvailability) o;
        return requiresDocuments == that.requiresDocuments && section.equals(that.section) && checkInWindow.equals(that.checkInWindow) && passengerRequirements.equals(that.passengerRequirements) && permittedDocuments.equals(that.permittedDocuments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, checkInWindow, passengerRequirements, requiresDocuments, permittedDocuments);
    }

    @Override
    public String toString() {
        return "CheckInAvailability{"
                + "section=" + section
                + ", checkInWindow=" + checkInWindow
                + ", passengerRequirements=" + passengerRequirements
                + ", requiresDocuments=" + requiresDocuments
                + ", permittedDocuments=" + permittedDocuments
                + '}';
    }
}
