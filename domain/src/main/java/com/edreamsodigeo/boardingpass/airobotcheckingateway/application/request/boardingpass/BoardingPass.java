package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckInRequest;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;

public class BoardingPass {

    private final BoardingPassId id;
    private final CheckInRequest checkInRequest;
    private final Section section;
    private final Passenger passenger;
    private final Status status;
    private final ProviderPassengerJourneyId providerPassengerJourneyId;

    public BoardingPass(BoardingPassId id, Section section, Passenger passenger, Status status, ProviderPassengerJourneyId providerPassengerJourneyId, CheckInRequest checkInRequest) {
        this.id = id;
        this.section = section;
        this.passenger = passenger;
        this.status = status;
        this.providerPassengerJourneyId = providerPassengerJourneyId;
        this.checkInRequest = checkInRequest;
    }

    public BoardingPassId id() {
        return id;
    }

    public CheckInRequest checkInRequest() {
        return checkInRequest;
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

    public ProviderPassengerJourneyId providerPassengerJourneyId() {
        return providerPassengerJourneyId;
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
