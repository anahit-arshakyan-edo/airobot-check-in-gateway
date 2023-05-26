package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import java.util.Objects;

public final class RequestId {

    private final String id;

    private RequestId(String id) {
        this.id = id;
    }

    public static RequestId from(String id) {
        return new RequestId(id);
    }

    public static RequestId notAssigned() {
        return new RequestId(null);
    }

    public String valueString() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RequestId that = (RequestId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
