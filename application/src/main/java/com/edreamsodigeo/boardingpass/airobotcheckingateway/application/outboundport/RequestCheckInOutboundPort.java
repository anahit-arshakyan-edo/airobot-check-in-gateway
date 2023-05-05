package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.itinerary.ProviderReferenceId;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.segment.SegmentCheckIn;

public interface RequestCheckInOutboundPort {
    SegmentCheckIn request(ProviderReferenceId providerReferenceId, SegmentCheckIn segmentCheckIn);
}
