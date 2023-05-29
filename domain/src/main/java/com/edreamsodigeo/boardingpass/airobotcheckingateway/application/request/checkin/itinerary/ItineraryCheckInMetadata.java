package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary;

import java.util.Objects;

public final class ItineraryCheckInMetadata {

    private final ItineraryCheckInId id;
    private final ProviderReferenceId referenceId;

    private ItineraryCheckInMetadata(ItineraryCheckInId id, ProviderReferenceId referenceId) {
        this.id = id;
        this.referenceId = referenceId;
    }

    public static ItineraryCheckInMetadata from(ItineraryCheckInId id, ProviderReferenceId referenceId) {
        return new ItineraryCheckInMetadata(id, referenceId);
    }

    public ItineraryCheckInId id() {
        return id;
    }

    public ProviderReferenceId referenceId() {
        return referenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItineraryCheckInMetadata that = (ItineraryCheckInMetadata) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
