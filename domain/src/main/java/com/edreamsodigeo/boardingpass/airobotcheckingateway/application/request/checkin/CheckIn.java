package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;

import java.util.List;
import java.util.stream.Collectors;

public class CheckIn {

    private final CheckInId id;
    private final long referenceId;
    private final List<BoardingPass> boardingPasses;

    public CheckIn(CheckInId id, long referenceId, List<BoardingPass> boardingPasses) {
        this.id = id;
        this.referenceId = referenceId;
        this.boardingPasses = boardingPasses;
    }

    public CheckInId id() {
        return id;
    }

    public long referenceId() {
        return referenceId;
    }

    public List<Passenger> passengers() {
        return boardingPasses.stream()
                .map(BoardingPass::passenger)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<BoardingPass> boardingPasses() {
        return boardingPasses;
    }

    public List<CheckInRequest> checkInRequests() {
        return boardingPasses.stream()
                .map(BoardingPass::checkInRequest)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Section> sections() {
        return boardingPasses.stream()
                .map(BoardingPass::section)
                .distinct()
                .collect(Collectors.toList());
    }
}
