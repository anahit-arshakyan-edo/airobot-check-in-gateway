package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;

public interface CheckInOutboundPort {

    void save(CheckIn checkIn);

}
