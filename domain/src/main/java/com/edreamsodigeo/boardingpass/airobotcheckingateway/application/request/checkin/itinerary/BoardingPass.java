package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.section.Section;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.Passenger;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.validation.Checker;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BoardingPass {

    private final BoardingPassId id;
    private final Section section;
    private final Passenger passenger;
    private Status status;
    private ProviderPassengerSectionId providerPassengerSectionId;

    private BoardingPass(BoardingPassId id, Section section,
                         Passenger passenger, Status status,
                         ProviderPassengerSectionId providerPassengerSectionId) {
        this.id = id;
        this.section = section;
        this.passenger = passenger;
        this.status = status;
        this.providerPassengerSectionId = providerPassengerSectionId;
    }

    public static boolean haveMoreThanOnePnr(List<BoardingPass> boardingPasses) {
        return pnrs(boardingPasses).size() > 1;
    }

    private static List<String> pnrs(List<BoardingPass> boardingPasses) {
        return boardingPasses.stream()
                .map(BoardingPass::section)
                .distinct()
                .map(Section::pnr)
                .distinct()
                .collect(Collectors.toList());
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

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setProviderPassengerSectionId(ProviderPassengerSectionId providerPassengerSectionId) {
        this.providerPassengerSectionId = providerPassengerSectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BoardingPass that = (BoardingPass) o;
        return section.equals(that.section) && passenger.equals(that.passenger);
    }

    @Override
    public int hashCode() {
        return Objects.hash(section, passenger);
    }

    public static BoardingPass.BoardingPassBuilder builder() {
        return new BoardingPass.BoardingPassBuilder();
    }

    public static class BoardingPassBuilder {

        private BoardingPassId id;
        private Section section;
        private Passenger passenger;
        private Status status;
        private ProviderPassengerSectionId providerPassengerSectionId;

        public BoardingPassBuilder withId(BoardingPassId id) {
            this.id = id;
            return this;
        }

        public BoardingPassBuilder withSection(Section section) {
            this.section = section;
            return this;
        }

        public BoardingPassBuilder withPassenger(Passenger passenger) {
            this.passenger = passenger;
            return this;
        }

        public BoardingPassBuilder withStatus(Status status) {
            this.status = status;
            return this;
        }

        public BoardingPassBuilder withProviderPassengerSectionId(ProviderPassengerSectionId providerPassengerSectionId) {
            this.providerPassengerSectionId = providerPassengerSectionId;
            return this;
        }

        public BoardingPass build() {
            BoardingPass boardingPass = new BoardingPass(this.id, this.section, this.passenger, this.status, this.providerPassengerSectionId);
            Checker.checkValidityWithJSR380(boardingPass);
            return boardingPass;
        }

    }
}
