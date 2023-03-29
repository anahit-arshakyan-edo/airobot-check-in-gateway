package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section;

import java.util.Objects;

public class ProviderSectionId {

    private final long id;

    public ProviderSectionId(long id) {
        this.id = id;
    }

    public long valueLong() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderSectionId that = (ProviderSectionId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
