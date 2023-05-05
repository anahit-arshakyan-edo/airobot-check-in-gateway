package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.outboundport.GetAvailabilityOutboundPort;
import com.google.inject.Inject;

import javax.annotation.PostConstruct;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import static com.edreams.configuration.ConfigurationEngine.injectMembers;

@Stateless
@Local(GetAvailabilityUseCase.class)
public class GetAvailabilityUseCaseImpl implements GetAvailabilityUseCase {

    @Inject
    private GetAvailabilityOutboundPort airobot;

    @PostConstruct
    public void init() {
        injectMembers(this);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Availability getAvailability(AvailabilityRequest availabilityRequest) {
        return airobot.getAvailability(availabilityRequest);
    }
}
