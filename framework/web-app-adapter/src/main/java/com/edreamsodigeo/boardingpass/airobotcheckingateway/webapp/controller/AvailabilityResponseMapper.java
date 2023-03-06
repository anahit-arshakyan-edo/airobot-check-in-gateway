package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.EnumMap;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.CheckInWindow;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentRequirement;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.PassengerRequirement;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.SectionAvailability;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AvailabilityResponseMapper {

    public CheckInAvailabilityResponse map(Availability availability) {
        boolean available = availability.isAvailable();
        boolean requiresApi = availability.isRequiresApi();

        List<SectionAvailability> availabilities = availability.sectionAvailabilities();
        List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInAvailability> availaibilitiesDto = availabilities.stream()
                .map(this::mapChecInAvailability)
                .collect(Collectors.toList());

        return CheckInAvailabilityResponse.builder()
                .withAvailable(available)
                .withRequiresAPI(requiresApi)
                .withAvailabilities(availaibilitiesDto)
                .build();
    }

    private com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInAvailability mapChecInAvailability(SectionAvailability sectionAvailability) {
        return com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInAvailability.builder()
                .withSection(mapSection(sectionAvailability.section()))
                .withCheckInWindow(mapCheckInWindow(sectionAvailability.checkInWindow()))
                .withPassengerRequirements(mapPassengerRequirements(sectionAvailability.passengerRequirements()))
                .withRequiresDocuments(sectionAvailability.requiresDocuments())
                .withPermittedDocuments(mapPermittedDocuments(sectionAvailability.permittedDocuments()))
                .build();
    }

    private Map<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document> mapPermittedDocuments(List<Document> documents) {
        Map<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document> result =
                new HashMap<>();

        for (Document document : documents) {
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document documentDto = this.mapPermittedDocument(document);
            result.put(documentDto.getType(), documentDto);
        }
        return result;
    }

    private com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document mapPermittedDocument(Document document) {
        return com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document.builder()
                .withType(mapDocumentType(document.documentType()))
                .withRequirements(this.mapDocumentRequirements(document.documentRequirements()))
                .build();
    }

    private List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement> mapDocumentRequirements(List<DocumentRequirement> documentRequirements) {
        return documentRequirements.stream()
                .map(this::mapDocumentRequirement)
                .collect(Collectors.toList());
    }

    private com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement mapDocumentRequirement(DocumentRequirement requirement) {
        EnumMap<DocumentRequirement, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement> map = new EnumMap<>();
        map.put(DocumentRequirement.COUNTRY, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement.COUNTRY);
        map.put(DocumentRequirement.EXPIRE_DATE, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement.EXPIRE_DATE);
        map.put(DocumentRequirement.ISSUE_DATE, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement.ISSUE_DATE);
        map.put(DocumentRequirement.NUMBER, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement.NUMBER);

        return map.getOrThrow(requirement);
    }

    private com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType mapDocumentType(DocumentType type) {
        EnumMap<DocumentType, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType> map = new EnumMap<>();
        map.put(DocumentType.NATIONAL_ID, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType.NATIONAL_ID);
        map.put(DocumentType.PASSPORT, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType.PASSPORT);

        return map.getOrThrow(type);
    }

    private List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.PassengerRequirement> mapPassengerRequirements(List<PassengerRequirement> passengerRequirements) {
        return passengerRequirements.stream()
                .map(this::mapPassengerRequirement)
                .collect(Collectors.toList());
    }

    private com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.PassengerRequirement mapPassengerRequirement(PassengerRequirement passengerRequirement) {
        EnumMap<PassengerRequirement, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.PassengerRequirement> map = new EnumMap<>();
        map.put(PassengerRequirement.GENDER, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.PassengerRequirement.GENDER);
        map.put(PassengerRequirement.DATE_OF_BIRTH, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.PassengerRequirement.DATE_OF_BIRTH);
        map.put(PassengerRequirement.NATIONALITY, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.PassengerRequirement.NATIONALITY);

        return map.getOrThrow(passengerRequirement);
    }

    private com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInWindow mapCheckInWindow(CheckInWindow checkInWindow) {
        return com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInWindow.builder()
                .withOpeningTime(checkInWindow.openingTime())
                .withClosingTime(checkInWindow.closingTime())
                .build();
    }

    private com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section mapSection(Section section) {
        return com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section.builder()
                .withOperatingCarrier(section.getAirlineIataCode())
                .withMarketingCarrier(section.getAirlineIataCode())
                .withDepartureAirport(section.getDepartureAirportIataCode())
                .withArrivalAirport(section.getArrivalAirportIataCode())
                .build();
    }
}
