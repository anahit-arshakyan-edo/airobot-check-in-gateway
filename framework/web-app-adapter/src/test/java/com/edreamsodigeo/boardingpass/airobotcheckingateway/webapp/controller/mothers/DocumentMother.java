package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers;

import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType;

import java.util.Map;

import static java.util.Collections.singletonList;

public class DocumentMother {

    public static final DocumentType NATIONAL_ID_DTO = com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType.NATIONAL_ID;

    public static final DocumentRequirement NUMBER_DTO = com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentRequirement.NUMBER;

    public static final com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document DOCUMENT_DTO =
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document.builder()
                    .withType(NATIONAL_ID_DTO)
                    .withRequirements(singletonList(NUMBER_DTO))
                    .build();

    private static Map<com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.DocumentType, com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.Document> permittedDocumentsDto
            = Map.of(NATIONAL_ID_DTO, DOCUMENT_DTO);
}
