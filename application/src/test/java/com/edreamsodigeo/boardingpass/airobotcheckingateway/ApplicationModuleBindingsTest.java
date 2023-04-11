package com.edreamsodigeo.boardingpass.airobotcheckingateway;


import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.ApplicationModuleBindings;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.availability.GetAvailabilityUseCaseImpl;
import com.edreamsodigeo.boardingpass.airobotcheckingateway.application.request.CreateCheckInUseCaseImpl;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.testng.Assert.assertSame;


public class ApplicationModuleBindingsTest {

    @Mock
    private GetAvailabilityUseCaseImpl getAvailabilityUseCase;
    @Mock
    private CreateCheckInUseCaseImpl createCheckInUseCaseImpl;

    @BeforeMethod
    public void setUp() {
        openMocks(this);
    }

    @Test
    public void testBindings() {
        Injector injector = Guice.createInjector(binder -> binder.bind(GetAvailabilityUseCaseImpl.class).toInstance(getAvailabilityUseCase), binder -> binder.bind(CreateCheckInUseCaseImpl.class).toInstance(createCheckInUseCaseImpl), new ApplicationModuleBindings());

        assertSame(injector.getInstance(GetAvailabilityUseCaseImpl.class), getAvailabilityUseCase);
        assertSame(injector.getInstance(CreateCheckInUseCaseImpl.class), createCheckInUseCaseImpl);

    }
}
