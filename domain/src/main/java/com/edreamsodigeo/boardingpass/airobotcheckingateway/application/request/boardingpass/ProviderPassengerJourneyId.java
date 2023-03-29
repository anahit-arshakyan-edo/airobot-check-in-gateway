package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import java.util.Objects;

public class ProviderPassengerJourneyId {

    private final long id;

    public ProviderPassengerJourneyId(long id) {
        this.id = id;
    }

    public long valueLong() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProviderPassengerJourneyId that = (ProviderPassengerJourneyId) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
