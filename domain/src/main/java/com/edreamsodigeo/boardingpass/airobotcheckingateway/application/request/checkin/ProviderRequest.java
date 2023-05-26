package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.DeliveryOptions;

import java.util.List;
import java.util.Objects;

public final class ProviderRequest {

    private final ProviderRequestId providerRequestId;
    private RequestId requestId;
    private final List<BoardingPass> boardingPasses;
    private final DeliveryOptions deliveryOptions;

    private ProviderRequest(ProviderRequestId providerRequestId, RequestId requestId, List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        this.providerRequestId = providerRequestId;
        this.requestId = requestId;
        this.boardingPasses = boardingPasses;
        this.deliveryOptions = deliveryOptions;
    }

    public static ProviderRequest from(String providerRequestId, String requestId, List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        return new ProviderRequest(ProviderRequestId.from(providerRequestId), RequestId.from(requestId), boardingPasses, deliveryOptions);
    }

    public static ProviderRequest from(List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        return new ProviderRequest(ProviderRequestId.create(), RequestId.notAssigned(), boardingPasses, deliveryOptions);
    }

    public ProviderRequestId providerRequestId() {
        return providerRequestId;
    }

    public RequestId requestId() {
        return requestId;
    }

    public List<BoardingPass> boardingPasses() {
        return boardingPasses;
    }

    public DeliveryOptions deliveryOptions() {
        return deliveryOptions;
    }

    public void setRequestId(RequestId requestId) {
        this.requestId = requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProviderRequest that = (ProviderRequest) o;
        return requestId.equals(that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }
}
