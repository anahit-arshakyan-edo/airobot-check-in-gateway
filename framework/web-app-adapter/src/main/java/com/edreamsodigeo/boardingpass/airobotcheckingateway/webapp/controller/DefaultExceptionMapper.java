package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.controller;

import com.edreamsodigeo.boardingpass.airobotcheckingateway.domain.availability.InvalidAvailabilityRequestException;
import com.edreamsodigeo.boardingpass.itinerarycheckinproviderapi.v1.exception.GatewayException;
import com.google.common.collect.ImmutableMap;
import com.odigeo.commons.rest.error.SimpleExceptionBean;
import com.odigeo.commons.rest.error.jackson.ExtractorFactory;
import org.apache.log4j.Logger;
import org.jboss.resteasy.spi.BadRequestException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static com.google.common.base.Throwables.getRootCause;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static org.apache.log4j.Level.ERROR;
import static org.apache.log4j.Level.WARN;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<RuntimeException> {

    private static final Logger LOGGER = Logger.getLogger(DefaultExceptionMapper.class);
    private static final MappedError UNEXPECTED_EXCEPTION = new MappedError(500, GatewayException.class);
    private static final ImmutableMap<Class<? extends Exception>, MappedError> EXCEPTION_MAPPER = ImmutableMap.of(
            InvalidAvailabilityRequestException.class, new MappedError(400, BadRequestException.class)
    );

    public DefaultExceptionMapper() {

    }

    @Override
    public Response toResponse(RuntimeException e) {
        MappedError mappedError = EXCEPTION_MAPPER.getOrDefault(e.getClass(), UNEXPECTED_EXCEPTION);

        boolean isWarning = mappedError.status < 500;
        LOGGER.log(isWarning ? WARN : ERROR, mappedError.type, getRootCause(e));

        return response(mappedError, e.getMessage());
    }

    private Response response(MappedError mappedError, String message) {
        SimpleExceptionBean bean = new SimpleExceptionBean();
        bean.setType(mappedError.type);
        bean.setMessage(message);
        return Response.status(mappedError.status).type(APPLICATION_JSON_TYPE).entity(bean).build();
    }

    private static class MappedError {
        private static final ExtractorFactory EXTRACTOR_FACTORY = new ExtractorFactory();
        private final int status;
        private final String type;

        MappedError(int status, Class<? extends Throwable> type) {
            this.status = status;
            this.type = EXTRACTOR_FACTORY.newBestNameExtractor(type).extract();
        }
    }
}
