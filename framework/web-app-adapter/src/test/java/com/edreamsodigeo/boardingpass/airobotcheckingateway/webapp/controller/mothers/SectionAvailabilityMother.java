package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.Document;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.SectionAvailability;

import java.util.Map;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.DocumentRequirement.NUMBER;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.DocumentType.NATIONAL_ID;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.PassengerRequirement.GENDER;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.CheckInWindowMother.CHECK_IN_WINDOW;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.CheckInWindowMother.CHECK_IN_WINDOW_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.DocumentMother.DOCUMENT_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.DocumentMother.NATIONAL_ID_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.PassengerRequirementsMother.GENDER_DTO;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.SectionMother.SECTION;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.SectionMother.SECTION_DTO;
import static java.util.Collections.singletonList;

public class SectionAvailabilityMother {

    public static final SectionAvailability SECTION_AVAILABILITY =
            new SectionAvailability(
                    SECTION,
                    CHECK_IN_WINDOW,
                    singletonList(GENDER),
                    true,
                    singletonList(new Document(NATIONAL_ID, singletonList(NUMBER))));

    public static final com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInAvailability CHECK_IN_AVAILABILITY_DTO =
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInAvailability.builder()
                    .withSection(SECTION_DTO)
                    .withCheckInWindow(CHECK_IN_WINDOW_DTO)
                    .withPassengerRequirements(singletonList(GENDER_DTO))
                    .withRequiresDocuments(true)
                    .withPermittedDocuments(Map.of(NATIONAL_ID_DTO, DOCUMENT_DTO))
                    .build();
}
