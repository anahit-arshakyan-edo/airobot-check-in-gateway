package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger;

import com.odigeo.commons.uuid.UUIDGenerator;

import java.util.UUID;

public final class PassengerId {

    private final UUID id;

    private PassengerId(UUID id) {
        this.id = id;
    }

    public static PassengerId from(String id) {
        return new PassengerId(UUID.fromString(id));
    };

    public static PassengerId create() {
        return new PassengerId(UUIDGenerator.getInstance().generateUUID());
    };

    public String value() {
        return id.toString();
    }
}
