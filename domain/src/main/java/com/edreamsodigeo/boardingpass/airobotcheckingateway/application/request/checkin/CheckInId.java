package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import com.odigeo.commons.uuid.UUIDGenerator;
import com.odigeo.commons.uuid.UUIDSerializer;

import java.util.Objects;
import java.util.UUID;

public final class CheckInId {

    private final UUID id;

    private CheckInId(UUID id) {
        this.id = id;
    }

    public static CheckInId from(String id) {
        return new CheckInId(UUIDSerializer.fromBytes(id.getBytes()));
    }

    public static CheckInId create() {
        return new CheckInId(UUIDGenerator.getInstance().generateUUID());
    }

    public UUID value() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInId checkInId = (CheckInId) o;
        return id.equals(checkInId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
