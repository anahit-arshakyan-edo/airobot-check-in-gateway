package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

import com.odigeo.commons.uuid.UUIDGenerator;

import java.util.Objects;
import java.util.UUID;

public final class PassengerId {

    private final UUID id;

    private PassengerId(UUID id) {
        this.id = id;
    }

    public static PassengerId from(String id) {
        return new PassengerId(UUID.fromString(id));
    }

    public static PassengerId create() {
        return new PassengerId(UUIDGenerator.getInstance().generateUUID());
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
        PassengerId that = (PassengerId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
