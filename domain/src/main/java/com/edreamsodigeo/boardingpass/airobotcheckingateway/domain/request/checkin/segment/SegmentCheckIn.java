package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.Section;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SegmentCheckIn {

    private final SegmentCheckInId id;
    private final ProviderRequestId providerRequestId;
    private final BoardingPassDeliveryCustomization boardingPassDeliveryCustomization;
    private final List<BoardingPass> boardingPasses;

    public SegmentCheckIn(SegmentCheckInId id, ProviderRequestId providerRequestId, BoardingPassDeliveryCustomization boardingPassDeliveryCustomization, List<BoardingPass> boardingPasses) {
        this.id = id;
        this.providerRequestId = providerRequestId;
        this.boardingPassDeliveryCustomization = boardingPassDeliveryCustomization;
        this.boardingPasses = boardingPasses;
    }

    public SegmentCheckInId id() {
        return id;
    }

    public ProviderRequestId providerRequestId() {
        return providerRequestId;
    }

    public BoardingPassDeliveryCustomization boardingPassDeliveryCustomization() {
        return boardingPassDeliveryCustomization;
    }

    public List<Section> sections() {
        return boardingPasses.stream()
                .map(BoardingPass::section)
                .distinct()
                .collect(Collectors.toList());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SegmentCheckIn that = (SegmentCheckIn) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
