package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import java.util.Objects;

public final class ProviderPassengerSectionId {

    private final Long id;

    private ProviderPassengerSectionId(Long id) {
        this.id = id;
    }

    public static ProviderPassengerSectionId from(long id) {
        return new ProviderPassengerSectionId(id);
    }

    public static ProviderPassengerSectionId notAssigned() {
        return new ProviderPassengerSectionId(null);
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
        ProviderPassengerSectionId that = (ProviderPassengerSectionId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
