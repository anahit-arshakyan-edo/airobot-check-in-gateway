package com.edreamsodigeo.boardingpass.airobotcheckingateway.configuration;

import com.edreams.configuration.ConfiguredInPropertiesFile;
import com.google.inject.Singleton;
import lombok.Getter;
import lombok.Setter;

@Singleton
@ConfiguredInPropertiesFile
@Getter
@Setter
public class AirobotApiConfiguration {
    private String apiToken;
}
