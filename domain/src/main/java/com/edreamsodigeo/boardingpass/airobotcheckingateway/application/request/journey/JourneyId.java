package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.journey;

import com.odigeo.commons.uuid.UUIDGenerator;

import java.util.UUID;

public final class JourneyId {

    private final UUID id;

    private JourneyId(UUID id) {
        this.id = id;
    }

    public static JourneyId from(String id) {
        return new JourneyId(UUID.fromString(id));
    };

    public static JourneyId create() {
        return new JourneyId(UUIDGenerator.getInstance().generateUUID());
    };

    public String value() {
        return id.toString();
    }
}
