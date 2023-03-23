package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.boardingpass.BoardingPass;

import java.util.List;

public interface BoardingPassOutboundPort {

    void save(BoardingPass boardingPass);
    List<BoardingPass> getBoardingPasses(String deliveryRequestId);

}
