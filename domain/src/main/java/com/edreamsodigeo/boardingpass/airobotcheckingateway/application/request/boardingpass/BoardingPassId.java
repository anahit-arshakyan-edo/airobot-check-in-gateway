package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.journey.JourneyId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.PassengerId;

public class BoardingPassId {

    private final DeliveryId deliveryId;
    private final PassengerId passengerId;
    private final JourneyId journeyId;

    public BoardingPassId(DeliveryId deliveryId, PassengerId passengerId, JourneyId journeyId) {
        this.deliveryId = deliveryId;
        this.passengerId = passengerId;
        this.journeyId = journeyId;
    }

    public DeliveryId deliveryId() {
        return deliveryId;
    }

    public PassengerId passengerId() {
        return passengerId;
    }

    public JourneyId journeyId() {
        return journeyId;
    }
}
