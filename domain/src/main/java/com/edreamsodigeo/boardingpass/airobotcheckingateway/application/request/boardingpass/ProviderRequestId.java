package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import java.util.Objects;

public final class ProviderRequestId {

    private final String id;

    private ProviderRequestId(String id) {
        this.id = id;
    }

    public static ProviderRequestId from(String id) {
        return new ProviderRequestId(id);
    }

    public static ProviderRequestId notAssigned() {
        return new ProviderRequestId(null);
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
        ProviderRequestId that = (ProviderRequestId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
