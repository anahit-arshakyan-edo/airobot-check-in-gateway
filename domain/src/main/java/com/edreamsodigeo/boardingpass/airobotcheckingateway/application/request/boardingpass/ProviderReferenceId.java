package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import java.util.Objects;

public final class ProviderReferenceId {

    private final Long id;

    private ProviderReferenceId(Long id) {
        this.id = id;
    }

    public static ProviderReferenceId from(long id) {
        return new ProviderReferenceId(id);
    }

    public static ProviderReferenceId notAssigned() {
        return new ProviderReferenceId(null);
    }

    public long valueLong() {
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
        ProviderReferenceId that = (ProviderReferenceId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
