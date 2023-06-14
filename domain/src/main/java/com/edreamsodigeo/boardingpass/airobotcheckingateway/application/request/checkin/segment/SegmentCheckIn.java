package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.DeliveryOptions;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
        Map<String, Set<Section>> sectionsWithSamePnrMap = BoardingPass.sectionsWithSamePnrMap(boardingPasses);
        if (sectionsWithSamePnrMap.size() > 1) {
            return splitIntoSeparateProviderRequests(sectionsWithSamePnrMap, boardingPasses, deliveryOptions);
        } else {
            return bundleIntoSingleProviderRequest(boardingPasses, deliveryOptions);
        }
    }

    private static List<ProviderRequest> splitIntoSeparateProviderRequests(Map<String, Set<Section>> sectionsWithSamePnr, List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        List<ProviderRequest> providerRequests = new ArrayList<>();
        for (String pnr : sectionsWithSamePnr.keySet()) {
            ProviderRequest providerRequest = ProviderRequest.from(createBoardingPassWithSamePnr(sectionsWithSamePnr.get(pnr), boardingPasses), deliveryOptions);
            providerRequests.add(providerRequest);
        }
        return providerRequests;
    }

    private static List<BoardingPass> createBoardingPassWithSamePnr(Set<Section> sections, List<BoardingPass> boardingPasses) {
        List<BoardingPass> boardingPassesResult = new ArrayList<>();
        for (BoardingPass boardingPass : boardingPasses) {
            if (sections.contains(boardingPass.section())) {
                boardingPassesResult.add(boardingPass);
            }
        }
        return boardingPassesResult;
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
