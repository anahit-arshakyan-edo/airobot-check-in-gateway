package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.DeliveryOptions;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderRequestId;

import java.util.List;
import java.util.Objects;

public final class ProviderRequest {

    private ProviderRequestId providerRequestId;
    private final List<BoardingPass> boardingPasses;
    private final DeliveryOptions deliveryOptions;

    private ProviderRequest(ProviderRequestId providerRequestId, List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        this.providerRequestId = providerRequestId;
        this.boardingPasses = boardingPasses;
        this.deliveryOptions = deliveryOptions;
    }

    public static ProviderRequest from(String providerRequestId, List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        return new ProviderRequest(ProviderRequestId.from(providerRequestId), boardingPasses, deliveryOptions);
    }

    public static ProviderRequest from(List<BoardingPass> boardingPasses, DeliveryOptions deliveryOptions) {
        return new ProviderRequest(ProviderRequestId.notAssigned(), boardingPasses, deliveryOptions);
    }

    public ProviderRequestId providerRequestId() {
        return providerRequestId;
    }

    public List<BoardingPass> boardingPasses() {
        return boardingPasses;
    }

    public DeliveryOptions deliveryOptions() {
        return deliveryOptions;
    }

    public void setProviderRequestId(ProviderRequestId providerRequestId) {
        this.providerRequestId = providerRequestId;
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
        return providerRequestId.equals(that.providerRequestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(providerRequestId);
    }
}
