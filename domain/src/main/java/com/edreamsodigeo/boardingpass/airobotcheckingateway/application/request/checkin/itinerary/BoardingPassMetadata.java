package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary;

import java.util.Objects;

public final class BoardingPassMetadata {

    private final BoardingPassId boardingPassId;
    private final ProviderPassengerSectionId providerPassengerSectionId;

    private BoardingPassMetadata(BoardingPassId boardingPassId, ProviderPassengerSectionId providerPassengerSectionId) {
        this.boardingPassId = boardingPassId;
        this.providerPassengerSectionId = providerPassengerSectionId;
    }

    public static BoardingPassMetadata from(BoardingPassId boardingPassId, ProviderPassengerSectionId providerPassengerSectionId) {
        return new BoardingPassMetadata(boardingPassId, providerPassengerSectionId);
    }


    public BoardingPassId boardingPassId() {
        return boardingPassId;
    }

    public ProviderPassengerSectionId providerPassengerSectionId() {
        return providerPassengerSectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardingPassMetadata that = (BoardingPassMetadata) o;
        return boardingPassId.equals(that.boardingPassId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardingPassId);
    }
}
