package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

import java.util.Objects;

public class ProviderPassengerId {

    private final long id;

    public ProviderPassengerId(long id) {
        this.id = id;
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
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
