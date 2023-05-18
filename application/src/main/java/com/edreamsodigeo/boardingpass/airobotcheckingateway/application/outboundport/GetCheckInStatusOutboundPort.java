package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.ProviderRequestId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;

public interface GetCheckInStatusOutboundPort {
    SegmentCheckIn getStatus(ProviderRequestId requestId);
}
