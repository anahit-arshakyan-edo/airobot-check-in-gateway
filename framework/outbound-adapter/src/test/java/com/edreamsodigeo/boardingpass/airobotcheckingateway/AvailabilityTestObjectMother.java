package com.edreamsodigeo.boardingpass.airobotcheckingateway;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequestBuilder;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.CheckInWindow;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentRequirement;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.DocumentType;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.PassengerRequirement;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Passengers;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.SectionAvailability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.SectionBuilder;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.JourneyAvailability;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.PermittedDocuments;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class AvailabilityTestObjectMother {
    private static final com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.PermittedDocuments
            PERMITTED_DOCUMENTS_NATIONAL_ID_AND_PASSPORT_DTO = permittedDocuments();

    private static final com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.PermittedDocuments
            PERMITTED_DOCUMENTS_EMPTY_DTO = new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.PermittedDocuments();

    private static final Section SECTION = new SectionBuilder()
            .withAirline("IB")
            .withDeparture("BCN")
            .withArrival("MAD")
            .build();

    private static final Passengers PASSENGERS = new Passengers(singletonList("IT"), 0, 0, 0);

    public static final AvailabilityRequest AVAILABILITY_REQUEST =
            new AvailabilityRequestBuilder()
                    .withSection(SECTION)
                    .withPassengers(PASSENGERS)
                    .build();

    public static final com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.response.AvailabilityResponse
            AVAILABILITY_RESPONSE_DTO = availabilityResponse(journeysDto(PERMITTED_DOCUMENTS_NATIONAL_ID_AND_PASSPORT_DTO));

    public static final com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.response.AvailabilityResponse
            AVAILABILITY_RESPONSE_PERMITTED_DOCUMENTS_EMPTY_DTO = availabilityResponse(journeysDto(PERMITTED_DOCUMENTS_EMPTY_DTO));

    private static com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.response.AvailabilityResponse availabilityResponse(List<JourneyAvailability> journeys) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.response.AvailabilityResponse availabilityResponse =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.response.AvailabilityResponse();
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.AvailabilityData availabilityData =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.AvailabilityData();
        availabilityData.setAvailable(true);
        availabilityData.setRequiresApi(true);
        availabilityData.setJourneys(journeys);
        availabilityResponse.setData(availabilityData);
        return availabilityResponse;
    }

    private static List<com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.JourneyAvailability> journeysDto(PermittedDocuments permittedDocuments) {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.JourneyAvailability journeyDto =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.JourneyAvailability();
        journeyDto.setAirline("IB");
        journeyDto.setDepartureAirport("BCN");
        journeyDto.setArrivalAirport("MAD");
        journeyDto.setCheckInWindow(checkInWindow());
        journeyDto.setRequirements(Arrays.asList("pax_gender", "pax_date_of_birth", "pax_nationality"));
        journeyDto.setPermittedDocuments(permittedDocuments);
        journeyDto.setRequiresDocuments(true);
        journeyDto.setAvailable(true);
        return Collections.singletonList(journeyDto);
    }

    private static com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.CheckInWindow checkInWindow() {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.CheckInWindow checkInWindow =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.CheckInWindow();
        checkInWindow.setOpeningTime(360);
        checkInWindow.setClosingTime(60);
        return checkInWindow;
    }

    private static com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.PermittedDocuments permittedDocuments() {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.PermittedDocuments permittedDocuments =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.PermittedDocuments();

        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.NationalId nationalId = nationalId();
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passport passport = passport();

        permittedDocuments.setNationalId(nationalId);
        permittedDocuments.setPassport(passport);
        return permittedDocuments;
    }

    private static com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passport passport() {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passport passport =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.Passport();
        passport.setRequirements(Arrays.asList("doc_number", "doc_country", "doc_expire_date", "doc_issue_date"));
        return passport;
    }

    private static com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.NationalId nationalId() {
        com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.NationalId nationalId =
                new com.edreamsodigeo.boardingpass.airobotproviderapi.v1.getavailability.model.NationalId();
        nationalId.setRequirements(Arrays.asList("doc_number", "doc_country", "doc_expire_date", "doc_issue_date"));
        return nationalId;
    }

    private static final CheckInWindow CHECK_IN_WINDOW = new CheckInWindow(Duration.ofMinutes(360), Duration.ofMinutes(60));

    private static final List<PassengerRequirement> PASSENGER_REQUIREMENTS = Arrays.asList(PassengerRequirement.GENDER, PassengerRequirement.DATE_OF_BIRTH, PassengerRequirement.NATIONALITY);

    private static final List<DocumentRequirement> DOCUMENT_REQUIREMENTS = Arrays.asList(DocumentRequirement.NUMBER, DocumentRequirement.COUNTRY, DocumentRequirement.EXPIRE_DATE, DocumentRequirement.ISSUE_DATE);

    private static final List<Document> PERMITTED_DOCUMENTS =
            Arrays.asList(new Document(DocumentType.NATIONAL_ID, DOCUMENT_REQUIREMENTS), new Document(DocumentType.PASSPORT, DOCUMENT_REQUIREMENTS));

    private static final List<Document> PERMITTED_DOCUMENTS_EMPTY = emptyList();

    private static final SectionAvailability SECTION_AVAILABILITY =
            new SectionAvailability(SECTION, CHECK_IN_WINDOW, PASSENGER_REQUIREMENTS, true, PERMITTED_DOCUMENTS);

    private static final SectionAvailability CHECK_IN_AVAILABILITY_PERMITTED_DOCUMENTS_EMPTY =
            new SectionAvailability(SECTION, CHECK_IN_WINDOW, PASSENGER_REQUIREMENTS, true, PERMITTED_DOCUMENTS_EMPTY);

    public static final Availability AVAILABILITY =
            new Availability(true, true, singletonList(SECTION_AVAILABILITY));

    public static final Availability AVAILABILITY_PERMITTED_DOCUMENTS_EMPTY =
            new Availability(true, true, singletonList(CHECK_IN_AVAILABILITY_PERMITTED_DOCUMENTS_EMPTY));

}
