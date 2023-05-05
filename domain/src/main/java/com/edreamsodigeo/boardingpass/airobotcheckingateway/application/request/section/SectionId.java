package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section;

import com.odigeo.commons.uuid.UUIDGenerator;

import java.util.Objects;
import java.util.UUID;

public final class SectionId {

    private final UUID id;

    private SectionId(UUID id) {
        this.id = id;
    }

    public static SectionId from(String id) {
        return new SectionId(UUID.fromString(id));
    }

    public static SectionId create() {
        return new SectionId(UUIDGenerator.getInstance().generateUUID());
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
        SectionId sectionId = (SectionId) o;
        return id.equals(sectionId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
