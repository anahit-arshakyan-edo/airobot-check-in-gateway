package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airline;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Airport;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.CheckInAvailability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.CheckInWindow;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentRequirement;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.PassengerRequirement;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Section;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TestObjectMother {
    private static Section section = new Section(new Airline("IB"), new Airport("BCN"), new Airport("MAD"));
    private static CheckInWindow checkInWindow = new CheckInWindow(Duration.ofMinutes(360), Duration.ofMinutes(60));
    private static List<PassengerRequirement> passengerRequirements = Collections.singletonList(PassengerRequirement.GENDER);
    private static List<DocumentRequirement> documentRequirements = Collections.singletonList(DocumentRequirement.NUMBER);
    private static List<Document> permittedDocuments =
            Collections.singletonList(new Document(DocumentType.NATIONAL_ID, documentRequirements));
    private static CheckInAvailability checkInAvailability =
            new CheckInAvailability(section, checkInWindow, passengerRequirements, true, permittedDocuments);
    private static List<CheckInAvailability> availabilities = Collections.singletonList(checkInAvailability);
    public static final Availability AVAILABILITY = new Availability(true, true, availabilities);
    public static final Availability AVAILABILITY_WITH_FALSE_RESULT = new Availability(false, false, Collections.emptyList());

    private static com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section sectionDto =
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Section.builder()
                    .withMarketingCarrier("IB")
                    .withOperatingCarrier("IB")
                    .withDepartureAirport("BCN")
                    .withArrivalAirport("MAD")
                    .build();
    private static com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInWindow checkInWindowDto =
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInWindow.builder()
                    .withOpeningTime(Duration.ofMinutes(360))
                    .withClosingTime(Duration.ofMinutes(60))
                    .build();
    private static List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.PassengerRequirement> passengerRequirementsDto =
            Collections.singletonList(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.PassengerRequirement.GENDER);
    private static com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document documentDto =
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document.builder()
                    .withType(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType.NATIONAL_ID)
                    .withRequirements(Collections.singletonList(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement.NUMBER))
                    .build();
    private static Map<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document> permittedDocumentsDto
            = Map.of(com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType.NATIONAL_ID, documentDto);
    private static com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInAvailability checkInAvailabilityDto =
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInAvailability.builder()
            .withSection(sectionDto)
            .withCheckInWindow(checkInWindowDto)
            .withPassengerRequirements(passengerRequirementsDto)
            .withRequiresDocuments(true)
            .withPermittedDocuments(permittedDocumentsDto)
            .build();
    private static List<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInAvailability> availabilitiesDto = Collections.singletonList(checkInAvailabilityDto);
    public static final CheckInAvailabilityResponse AVAILABILTY_RESPONSE = CheckInAvailabilityResponse.builder()
            .withAvailable(true)
            .withRequiresAPI(true)
            .withAvailabilities(availabilitiesDto)
            .build();
}
