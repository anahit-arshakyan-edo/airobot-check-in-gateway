package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Availability;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.AvailabilityRequestBuilder;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.request.CheckInAvailabilityRequest;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.response.CheckInAvailabilityResponse;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.Passengers.EMPTY_PASSENGERS;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.SectionAvailabilityMother.CHECK_IN_AVAILABILITY_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.SectionAvailabilityMother.SECTION_AVAILABILITY;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.SectionMother.SECTION;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.SectionMother.SECTION_DTO;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

public class AvailabilityRequestMother {

    public static final CheckInAvailabilityRequest CHECK_IN_AVAILABILITY_REQUEST_DTO =
            CheckInAvailabilityRequest.builder()
                    .withSections(singletonList(SECTION_DTO))
                    .build();

    public static final CheckInAvailabilityRequest CHECK_IN_AVAILABILITY_REQUEST_NO_SECTION_DTO =
            CheckInAvailabilityRequest.builder()
                    .withSections(emptyList())
                    .build();

    public static final AvailabilityRequest AVAILABILITY_REQUEST = new AvailabilityRequestBuilder()
            .withSection(SECTION)
            .withPassengers(EMPTY_PASSENGERS)
            .build();

    public static final Availability AVAILABILITY = new Availability(
            true,
            true,
            singletonList(SECTION_AVAILABILITY));

    public static final CheckInAvailabilityResponse CHECK_IN_AVAILABILITY_RESPONSE_DTO = CheckInAvailabilityResponse.builder()
            .withAvailable(true)
            .withRequiresAPI(true)
            .withAvailabilities(singletonList(CHECK_IN_AVAILABILITY_DTO))
            .build();
}
