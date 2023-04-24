package com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.request.section;

import java.util.Objects;

public final class ProviderSectionId {

    private final Long id;

    private ProviderSectionId(Long id) {
        this.id = id;
    }

    public static ProviderSectionId from(long id) {
        return new ProviderSectionId(id);
    }

    public static ProviderSectionId notAssigned() {
        return new ProviderSectionId(null);
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
        ProviderSectionId that = (ProviderSectionId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
