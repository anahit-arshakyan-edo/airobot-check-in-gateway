package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import com.odigeo.commons.uuid.UUIDGenerator;
import com.odigeo.commons.uuid.UUIDSerializer;

import java.util.Objects;
import java.util.UUID;

public final class BoardingPassId {

    private final UUID id;

    private BoardingPassId(UUID id) {
        this.id = id;
    }

    public static BoardingPassId from(String id) {
        return new BoardingPassId(UUIDSerializer.fromBytes(id.getBytes()));
    }

    public static BoardingPassId create() {
        return new BoardingPassId(UUIDGenerator.getInstance().generateUUID());
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
        BoardingPassId that = (BoardingPassId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
