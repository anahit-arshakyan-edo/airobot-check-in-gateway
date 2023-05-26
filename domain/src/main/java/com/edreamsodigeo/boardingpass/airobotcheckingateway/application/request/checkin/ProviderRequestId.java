package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin;

import com.odigeo.commons.uuid.UUIDGenerator;

import java.util.Objects;
import java.util.UUID;

public final class ProviderRequestId {

    private final UUID id;

    private ProviderRequestId(UUID id) {
        this.id = id;
    }

    public static ProviderRequestId from(String id) {
        return new ProviderRequestId(UUID.fromString(id));
    }

    public static ProviderRequestId from(UUID id) {
        return new ProviderRequestId(id);
    }

    public static ProviderRequestId create() {
        return new ProviderRequestId(UUIDGenerator.getInstance().generateUUID());
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
        ProviderRequestId that = (ProviderRequestId) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
