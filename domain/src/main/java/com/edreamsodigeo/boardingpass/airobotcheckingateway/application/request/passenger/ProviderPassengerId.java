package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

import java.util.Objects;

public final class ProviderPassengerId {

    private final Long id;

    private ProviderPassengerId(Long id) {
        this.id = id;
    }

    public static ProviderPassengerId from(long id) {
        return new ProviderPassengerId(id);
    }

    public static ProviderPassengerId notAssigned() {
        return new ProviderPassengerId(null);
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
        ProviderPassengerId that = (ProviderPassengerId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
