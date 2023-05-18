package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.DeliveryOptions;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class SegmentCheckIn {

    private final SegmentCheckInId id;
    private final List<ProviderRequest> providerRequests;

    private SegmentCheckIn(SegmentCheckInId id, List<ProviderRequest> providerRequests) {
        this.id = id;
        this.providerRequests = providerRequests;
    }

    public static SegmentCheckIn from(List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        return new SegmentCheckIn(SegmentCheckInId.create(), createProviderRequestsFor(boardingPasses, deliveryOptions));
    }

    public static SegmentCheckIn from(String uuid, List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        return new SegmentCheckIn(SegmentCheckInId.from(uuid), createProviderRequestsFor(boardingPasses, deliveryOptions));
    }

    public static SegmentCheckIn from(String uuid, List<ProviderRequest> providerRequests) {
        return new SegmentCheckIn(SegmentCheckInId.from(uuid), providerRequests);
    }

    private static List<ProviderRequest> createProviderRequestsFor(List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        if (BoardingPass.haveMoreThanOnePnr(boardingPasses)) {
            return splitIntoSeparateProviderRequests(boardingPasses, deliveryOptions);
        } else {
            return bundleIntoSingleProviderRequest(boardingPasses, deliveryOptions);
        }
    }

    private static List<ProviderRequest> splitIntoSeparateProviderRequests(List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        List<ProviderRequest> providerRequests = new ArrayList<>();
        for (BoardingPass boardingPass : boardingPasses) {
            ProviderRequest providerRequest = ProviderRequest.from(Collections.singletonList(boardingPass), deliveryOptions);
            providerRequests.add(providerRequest);
        }
        return providerRequests;
    }

    private static List<ProviderRequest> bundleIntoSingleProviderRequest(List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        ProviderRequest providerRequest = ProviderRequest.from(boardingPasses, deliveryOptions);
        return Collections.singletonList(providerRequest);
    }

    public SegmentCheckInId id() {
        return id;
    }

    public List<ProviderRequest> providerRequests() {
        return providerRequests;
    }

    public ProviderRequestId providerRequestsId() {
        return this.providerRequests.stream()
                .map(ProviderRequest::providerRequestId)
                .findAny().orElseThrow();
    }

    public List<BoardingPass> boardingPasses() {
        return providerRequests.stream()
                .map(ProviderRequest::boardingPasses)
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<Section> sections() {
        return boardingPasses().stream()
                .map(BoardingPass::section)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Passenger> passengers() {
        return boardingPasses().stream()
                .map(BoardingPass::passenger)
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
        SegmentCheckIn that = (SegmentCheckIn) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
