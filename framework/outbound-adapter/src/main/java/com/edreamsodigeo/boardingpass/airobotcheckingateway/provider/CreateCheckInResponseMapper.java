package com.edreamsodigeo.boardingpass.airobotcheckingateway.provider;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckInId;
import com.edreamsodigeo.boardingpass.airobotproviderapi.v1.createcheckin.response.CreateCheckInResponse;

import java.util.Collections;

public class CreateCheckInResponseMapper {

    public CheckIn map(CheckInId checkInId, CreateCheckInResponse createCheckInResponse) {
        return new CheckIn(checkInId, -1, Collections.emptyList());
    }

}
