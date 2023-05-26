package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import java.util.Objects;

public final class ProviderRequestMetadata {

    private final ProviderRequestId providerRequestId;
    private final RequestId requestId;

    private ProviderRequestMetadata(ProviderRequestId providerRequestId, RequestId requestId) {
        this.providerRequestId = providerRequestId;
        this.requestId = requestId;
    }

    public static ProviderRequestMetadata from(ProviderRequestId providerRequestId, RequestId requestId) {
        return new ProviderRequestMetadata(providerRequestId, requestId);
    }

    public ProviderRequestId providerRequestId() {
        return providerRequestId;
    }

    public RequestId requestId() {
        return requestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProviderRequestMetadata that = (ProviderRequestMetadata) o;
        return requestId.equals(that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }
}
