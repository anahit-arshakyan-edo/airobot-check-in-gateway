package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItineraryCheckIn {

    private final ItineraryCheckInId id;
    private final ProviderReferenceId referenceId;
    private final List<SegmentCheckIn> segmentCheckIns;

    public ItineraryCheckIn(ItineraryCheckInId id, List<SegmentCheckIn> segmentCheckIns) {
        this.id = id;
        this.referenceId = ProviderReferenceId.from(id.value().getMostSignificantBits());
        this.segmentCheckIns = segmentCheckIns;
    }

    public static ItineraryCheckIn from(ItineraryCheckInId id, List<SegmentCheckIn> segmentCheckIns) {
        return new ItineraryCheckIn(id, segmentCheckIns);
    }

    public static ItineraryCheckIn from(List<SegmentCheckIn> segmentCheckIns) {
        return new ItineraryCheckIn(ItineraryCheckInId.create(), segmentCheckIns);
    }

    public ItineraryCheckInId id() {
        return id;
    }

    public ProviderReferenceId referenceId() {
        return referenceId;
    }

    public List<SegmentCheckIn> segmentCheckIns() {
        return segmentCheckIns;
    }

    public List<BoardingPass> boardingPasses() {
        return segmentCheckIns.stream()
                .map(SegmentCheckIn::providerRequests)
                .flatMap(List::stream)
                .map(ProviderRequest::boardingPasses)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<Section> sections() {
        return segmentCheckIns().stream()
                .map(SegmentCheckIn::sections)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<Passenger> passengers() {
        return segmentCheckIns().stream()
                .map(SegmentCheckIn::passengers)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<ProviderRequest> providerRequests() {
        return segmentCheckIns().stream()
                .map(SegmentCheckIn::providerRequests)
                .flatMap(List::stream)
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
