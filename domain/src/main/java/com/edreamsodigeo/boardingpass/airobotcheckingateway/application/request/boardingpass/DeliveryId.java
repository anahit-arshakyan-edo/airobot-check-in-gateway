package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

public final class DeliveryId {

    private final String requestId;

    private DeliveryId(String requestId) {
        this.requestId = requestId;
    }

    public static DeliveryId from(String requestId) {
        return new DeliveryId(requestId);
    }

    public String value() {
        return requestId;
    }
}
