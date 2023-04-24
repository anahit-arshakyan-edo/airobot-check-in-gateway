package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.itinerary;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section.Section;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItineraryCheckIn {

    private final ItineraryCheckInId id;
    private final long referenceId;
    private final List<SegmentCheckIn> segmentCheckIns;

    public ItineraryCheckIn(ItineraryCheckInId id, List<SegmentCheckIn> segmentCheckIns) {
        this.id = id;
        this.referenceId = id.value().getMostSignificantBits();
        this.segmentCheckIns = segmentCheckIns;
    }

    public ItineraryCheckInId id() {
        return id;
    }

    public long referenceId() {
        return referenceId;
    }

    public List<SegmentCheckIn> segmentCheckIns() {
        return segmentCheckIns;
    }

    public List<BoardingPass> boardingPasses() {
        return segmentCheckIns.stream()
                .map(SegmentCheckIn::boardingPasses)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<Section> sections() {
        return segmentCheckIns().stream()
                .map(SegmentCheckIn::sections)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Passenger> passengers() {
        return segmentCheckIns().stream()
                .map(SegmentCheckIn::passengers)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItineraryCheckIn that = (ItineraryCheckIn) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}