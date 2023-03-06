package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.CheckInWindow;

import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.DurationMother.ONE_HOUR;
import static com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller.mothers.DurationMother.SIX_HOURS;

public class CheckInWindowMother {
    public static final CheckInWindow CHECK_IN_WINDOW = new CheckInWindow(SIX_HOURS, ONE_HOUR);

    public static final com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInWindow CHECK_IN_WINDOW_DTO =
            com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.model.CheckInWindow.builder()
                    .withOpeningTime(SIX_HOURS)
                    .withClosingTime(ONE_HOUR)
                    .build();
}
