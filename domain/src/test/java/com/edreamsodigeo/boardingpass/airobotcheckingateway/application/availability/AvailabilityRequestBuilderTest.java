package com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.ValidationException;
import org.testng.annotations.Test;

import static java.util.Collections.emptyList;
import static org.testng.Assert.assertEquals;

public class AvailabilityRequestBuilderTest {

    @Test
    public void unspecifiedPassengersAreConvertedInAnEmptyObject() {
        AvailabilityRequest request = new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .build();

        assertEquals(request.passengers(), Passengers.EMPTY_PASSENGERS);
    }

    @Test
    public void nullPassengersAreConvertedInAnEmptyObject() {
        AvailabilityRequest request = new AvailabilityRequestBuilder()
                .withPassengers(null)
                .withSection("IB", "BCN", "MXP")
                .build();

        assertEquals(request.passengers(), Passengers.EMPTY_PASSENGERS);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidAvailabilityRequestDueToNullSectionList() {
        new AvailabilityRequestBuilder()
                .withSections(null)
                .build();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidAvailabilityRequestDueToEmptySectionList() {
        new AvailabilityRequestBuilder()
                .withSections(emptyList())
                .build();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidAvailabilityRequestDueToOneNullSectionInTheList() {
        new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .withSection(null)
                .build();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidAvailabilityRequestDueToMissingAirline() {
        new AvailabilityRequestBuilder()
                .withSection("", "LSB", "MXP")
                .build();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidAvailabilityRequestDueToMissingDeparture() {
        new AvailabilityRequestBuilder()
                .withSection("IB", "", "MXP")
                .build();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidAvailabilityRequestDueToMissingArrival() {
        new AvailabilityRequestBuilder()
                .withSection("IB", "MXP", "")
                .build();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void invalidAvailabilityRequestDueToOneInvalidSectionInTheList() {
        new AvailabilityRequestBuilder()
                .withSection("IB", "BCN", "MXP")
                .withSection("", "BCN", "MXP")
                .build();
    }
}
