package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.RequestCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.SaveCheckInOutboundPort;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.checkin.CheckIn;
import com.google.inject.Inject;

public class CreateCheckInUseCaseImpl implements CreateCheckInUseCase {

    private final RequestCheckInOutboundPort requestCheckInOutboundPort;
    private final SaveCheckInOutboundPort saveCheckInOutboundPort;

    @Inject
    public CreateCheckInUseCaseImpl(RequestCheckInOutboundPort requestCheckInOutboundPort, SaveCheckInOutboundPort saveCheckInOutboundPort) {
        this.requestCheckInOutboundPort = requestCheckInOutboundPort;
        this.saveCheckInOutboundPort = saveCheckInOutboundPort;
    }

    @Override
    public CheckIn createCheckIn(CheckIn checkInToRequest) {
        CheckIn requestedCheckIn = requestCheckInOutboundPort.request(checkInToRequest);
        saveCheckInOutboundPort.save(requestedCheckIn);
        return requestedCheckIn;
    }

}
