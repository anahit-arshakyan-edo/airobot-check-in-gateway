package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary;

import com.odigeo.commons.uuid.UUIDGenerator;

import java.util.Objects;
import java.util.UUID;

public final class ItineraryCheckInId {

    private final UUID id;

    private ItineraryCheckInId(UUID id) {
        this.id = id;
    }

    public static ItineraryCheckInId from(String id) {
        return new ItineraryCheckInId(UUID.fromString(id));
    }

    public static ItineraryCheckInId create() {
        return new ItineraryCheckInId(UUIDGenerator.getInstance().generateUUID());
    }

    public UUID value() {
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
        ItineraryCheckInId itineraryCheckInId = (ItineraryCheckInId) o;
        return id.equals(itineraryCheckInId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
