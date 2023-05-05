package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment;

import com.odigeo.commons.uuid.UUIDGenerator;

import java.util.Objects;
import java.util.UUID;

public final class SegmentCheckInId {

    private final UUID id;

    private SegmentCheckInId(UUID id) {
        this.id = id;
    }

    public static SegmentCheckInId from(String id) {
        return new SegmentCheckInId(UUID.fromString(id));
    }

    public static SegmentCheckInId create() {
        return new SegmentCheckInId(UUIDGenerator.getInstance().generateUUID());
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
        SegmentCheckInId that = (SegmentCheckInId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
