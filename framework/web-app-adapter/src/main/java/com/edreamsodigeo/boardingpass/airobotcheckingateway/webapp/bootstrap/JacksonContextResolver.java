package com.edreamsodigeo.boardingpass.airobotcheckingateway.webapp.bootstrap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonContextResolver implements ContextResolver<ObjectMapper> {

    private final ObjectMapper objectMapper = init();

    @Override
    public ObjectMapper getContext(Class<?> objectType) {
        return objectMapper;
    }

    private ObjectMapper init() {
        ObjectMapper objectMapperWithJavaTime = new ObjectMapper();
        objectMapperWithJavaTime.registerModule(new JavaTimeModule());
        return objectMapperWithJavaTime;
    }
}
