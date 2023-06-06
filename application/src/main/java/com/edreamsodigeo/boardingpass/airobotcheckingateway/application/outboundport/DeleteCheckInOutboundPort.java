package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.RequestId;

public interface DeleteCheckInOutboundPort {
    void deleteCheckIn(RequestId requestId);
}
