package com.edreamsodigeo.boardingpass.airobotcheckingateway.commons;

import com.google.inject.Singleton;

import java.util.Optional;

@Singleton
public class SystemEnv {

    public Optional<String> get(String envName) {
        return Optional.ofNullable(System.getenv(envName));
    }
}
