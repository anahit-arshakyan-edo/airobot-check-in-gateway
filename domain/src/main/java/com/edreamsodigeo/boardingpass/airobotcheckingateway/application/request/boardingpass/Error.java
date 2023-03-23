package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.journey.JourneyId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.passenger.PassengerId;

public class Error {

    private final PassengerId passengerId;
    private final JourneyId journeyId;
    private final DeliveryId deliveryId;
    private final String errorCode;

    public Error(PassengerId passengerId, JourneyId journeyId, DeliveryId deliveryId, String errorCode) {
        this.passengerId = passengerId;
        this.journeyId = journeyId;
        this.deliveryId = deliveryId;
        this.errorCode = errorCode;
    }

    public PassengerId passengerId() {
        return passengerId;
    }

    public JourneyId journeyId() {
        return journeyId;
    }

    public DeliveryId deliveryId() {
        return deliveryId;
    }

    public String errorCode() {
        return errorCode;
    }
}
