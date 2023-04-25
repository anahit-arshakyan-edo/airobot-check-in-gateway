package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.EnumMap;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.CheckInWindow;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.DocumentRequirement;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.PassengerRequirement;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.SectionAvailability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.SectionBuilder;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.JourneyAvailability;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.PermittedDocuments;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AvailabilityResponseMapper {

    private static final EnumMap<String, PassengerRequirement> PASSENGER_REQUIREMENT_ENUM_MAP = new EnumMap<String, PassengerRequirement>()
            .put("pax_gender", PassengerRequirement.GENDER)
            .put("pax_date_of_birth", PassengerRequirement.DATE_OF_BIRTH)
            .put("pax_nationality", PassengerRequirement.NATIONALITY);

    private static final EnumMap<String, DocumentRequirement> DOCUMENT_REQUIREMENT_ENUM_MAP = new EnumMap<String, DocumentRequirement>()
            .put("doc_country", DocumentRequirement.COUNTRY)
            .put("doc_expire_date", DocumentRequirement.EXPIRE_DATE)
            .put("doc_issue_date", DocumentRequirement.ISSUE_DATE)
            .put("doc_number", DocumentRequirement.NUMBER);

    public Availability map(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.response.AvailabilityResponse response) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.AvailabilityData availabilityDataDto = response.getData();
        boolean available = availabilityDataDto.isAvailable();
        boolean requiresApi = availabilityDataDto.isRequiresApi();
        List<SectionAvailability> checkInAvailabilities = mapJourneysAvailabilityDto(availabilityDataDto.getJourneys());

        return new Availability(available, requiresApi, checkInAvailabilities);
    }

    private List<SectionAvailability> mapJourneysAvailabilityDto(List<JourneyAvailability> journeysDto) {
        return journeysDto.stream()
                .map(this::mapJourneyAvailabilityDto)
                .collect(Collectors.toList());
    }

    private SectionAvailability mapJourneyAvailabilityDto(JourneyAvailability journeyAvailabilityDto) {
        Section section = new SectionBuilder()
                .withAirline(journeyAvailabilityDto.getAirline())
                .withDeparture(journeyAvailabilityDto.getDepartureAirport())
                .withArrival(journeyAvailabilityDto.getArrivalAirport())
                .build();

        CheckInWindow checkInWindow = mapCheckInWindowDto(journeyAvailabilityDto.getCheckInWindow());

        List<PassengerRequirement> passengerRequirements = mapRequirementsDto(journeyAvailabilityDto.getRequirements());

        boolean requiresDocument = journeyAvailabilityDto.isRequiresDocuments();

        List<Document> permittedDocuments = mapPermittedDocumentsDto(journeyAvailabilityDto.getPermittedDocuments());

        return new SectionAvailability(
                section, checkInWindow, passengerRequirements, requiresDocument, permittedDocuments);
    }

    private List<PassengerRequirement> mapRequirementsDto(List<String> requirementsDto) {
        return requirementsDto.stream()
                .map(this::mapRequirementDto)
                .collect(Collectors.toList());
    }

    private PassengerRequirement mapRequirementDto(String requirementDto) {
        return PASSENGER_REQUIREMENT_ENUM_MAP.getOrThrow(requirementDto);
    }

    private CheckInWindow mapCheckInWindowDto(com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.CheckInWindow checkInWindowDto) {
        Duration openingTime = mapTimeDto(checkInWindowDto.getOpeningTime());
        Duration closingTime = mapTimeDto(checkInWindowDto.getClosingTime());
        return new CheckInWindow(openingTime, closingTime);
    }

    private Duration mapTimeDto(Integer openingTime) {
        return Duration.ofMinutes(openingTime);
    }

    private List<Document> mapPermittedDocumentsDto(PermittedDocuments permittedDocumentsDto) {
        List<Document> permittedDocuments = new ArrayList<>();

        Document nationalId = mapNationalIdDto(permittedDocumentsDto);
        if (nationalId != null) {
            permittedDocuments.add(nationalId);
        }

        Document passport = mapPassportDto(permittedDocumentsDto);
        if (passport != null) {
            permittedDocuments.add(passport);
        }

        return permittedDocuments;
    }

    private Document mapNationalIdDto(PermittedDocuments permittedDocumentsDto) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.NationalId nationalIdDto = permittedDocumentsDto.getNationalId();
        if (nationalIdDto == null) {
            return null;
        }

        List<DocumentRequirement> nationalIdRequirements = mapDocumentRequirementsDto(nationalIdDto.getRequirements());
        return new Document(DocumentType.NATIONAL_ID, nationalIdRequirements);
    }

    private Document mapPassportDto(PermittedDocuments permittedDocumentsDto) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passport passportDto = permittedDocumentsDto.getPassport();
        if (passportDto == null) {
            return null;
        }

        List<DocumentRequirement> passportRequirements = mapDocumentRequirementsDto(passportDto.getRequirements());
        return new Document(DocumentType.PASSPORT, passportRequirements);
    }


    private List<DocumentRequirement> mapDocumentRequirementsDto(List<String> requirementsDto) {
        return requirementsDto.stream()
                .map(this::mapDocumentRequirementDto)
                .collect(Collectors.toList());
    }

    private DocumentRequirement mapDocumentRequirementDto(String requirementDto) {
        return DOCUMENT_REQUIREMENT_ENUM_MAP.getOrThrow(requirementDto);
    }
}
