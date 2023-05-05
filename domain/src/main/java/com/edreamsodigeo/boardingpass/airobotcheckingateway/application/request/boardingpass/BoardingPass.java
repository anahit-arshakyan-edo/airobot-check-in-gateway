package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;

public class BoardingPass {

    private final BoardingPassId id;
    private final Section section;
    private final Passenger passenger;
    private final Status status;
    private final ProviderPassengerSectionId providerPassengerSectionId;

    public BoardingPass(BoardingPassId id, Section section, Passenger passenger, Status status, ProviderPassengerSectionId providerPassengerSectionId) {
        this.id = id;
        this.section = section;
        this.passenger = passenger;
        this.status = status;
        this.providerPassengerSectionId = providerPassengerSectionId;
    }

    public BoardingPassId id() {
        return id;
    }

    public Passenger passenger() {
        return passenger;
    }

    public Status status() {
        return status;
    }

    public Section section() {
        return section;
    }

    public ProviderPassengerSectionId providerPassengerSectionId() {
        return providerPassengerSectionId;
    }

    public boolean isFailed() {
        return Status.Code.FAILED.equals(this.status.code());
    }

    public boolean isSucceed() {
        return Status.Code.SUCCEED.equals(this.status.code());
    }

    public boolean isPending() {
        return Status.Code.PENDING.equals(this.status.code());
    }
}
