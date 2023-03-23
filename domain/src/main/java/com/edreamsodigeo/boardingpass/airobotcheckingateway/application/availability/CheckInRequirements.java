package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import java.util.List;
import java.util.Map;

public class CheckInRequirements {

    private final Section section;
    private final CheckInWindow checkInWindow;
    private final List<PassengerRequirement> passengerRequirements;
    private final boolean requiresDocuments;
    private final Map<DocumentType, Document> permittedDocuments;

    public CheckInRequirements(Section section, CheckInWindow checkInWindow, List<PassengerRequirement> passengerRequirements, boolean requiresDocuments, Map<DocumentType, Document> permittedDocuments) {
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

    public boolean isRequiresDocuments() {
        return requiresDocuments;
    }

    public Map<DocumentType, Document> permittedDocuments() {
        return permittedDocuments;
    }
}
