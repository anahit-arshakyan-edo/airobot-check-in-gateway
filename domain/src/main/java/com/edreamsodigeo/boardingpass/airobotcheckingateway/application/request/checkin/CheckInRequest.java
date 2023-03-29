package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;

import java.util.List;
import java.util.stream.Collectors;

public class CheckInRequest {

    private final CheckInRequestId id;
    private final String providerRequestId;
    private final List<BoardingPass> boardingPasses;

    public CheckInRequest(CheckInRequestId id, String providerRequestId, List<BoardingPass> boardingPasses) {
        this.id = id;
        this.providerRequestId = providerRequestId;
        this.boardingPasses = boardingPasses;
    }

    public CheckInRequestId id() {
        return id;
    }

    public String providerRequestId() {
        return providerRequestId;
    }

    public List<BoardingPass> boardingPasses() {
        return boardingPasses;
    }

    public List<Section> sections() {
        return boardingPasses.stream()
                .map(BoardingPass::section)
                .distinct()
                .collect(Collectors.toList());
    }

}
