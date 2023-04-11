package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;

public interface CreateCheckInUseCase {
    CheckIn createCheckIn(CheckIn checkIn);
}
