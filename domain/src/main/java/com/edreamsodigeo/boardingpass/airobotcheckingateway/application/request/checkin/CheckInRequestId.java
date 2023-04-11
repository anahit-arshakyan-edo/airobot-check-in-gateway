package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import com.odigeo.commons.uuid.UUIDGenerator;
import com.odigeo.commons.uuid.UUIDSerializer;

import java.util.Objects;
import java.util.UUID;

public final class CheckInRequestId {

    private final UUID id;

    private CheckInRequestId(UUID id) {
        this.id = id;
    }

    public static CheckInRequestId from(String id) {
        return new CheckInRequestId(UUIDSerializer.fromBytes(id.getBytes()));
    }

    public static CheckInRequestId create() {
        return new CheckInRequestId(UUIDGenerator.getInstance().generateUUID());
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
        CheckInRequestId that = (CheckInRequestId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
