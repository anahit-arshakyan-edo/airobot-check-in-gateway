package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import java.util.Objects;

public class CheckInRequest {

    private final CheckInRequestId id;
    private final String providerRequestId;

    public CheckInRequest(CheckInRequestId id, String providerRequestId) {
        this.id = id;
        this.providerRequestId = providerRequestId;
    }

    public CheckInRequestId id() {
        return id;
    }

    public String providerRequestId() {
        return providerRequestId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CheckInRequest that = (CheckInRequest) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
