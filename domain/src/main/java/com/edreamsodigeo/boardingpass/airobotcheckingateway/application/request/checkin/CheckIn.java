package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CheckIn {

    private final CheckInId id;
    private final long referenceId;
    private final List<CheckInRequest> checkInRequests;

    public CheckIn(CheckInId id, long referenceId, List<CheckInRequest> checkInRequests) {
        this.id = id;
        this.referenceId = referenceId;
        this.checkInRequests = checkInRequests;
    }

    public CheckInId id() {
        return id;
    }

    public long referenceId() {
        return referenceId;
    }

    public List<CheckInRequest> checkInRequests() {
        return checkInRequests;
    }

    public List<Passenger> passengers() {
        return checkInRequests.stream()
                .map(CheckInRequest::boardingPasses)
                .flatMap(Collection::stream)
                .map(BoardingPass::passenger)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<BoardingPass> boardingPasses() {
        return checkInRequests.stream()
                .map(CheckInRequest::boardingPasses)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
