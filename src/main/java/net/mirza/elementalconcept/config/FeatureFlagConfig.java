package net.mirza.elementalconcept.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "feature")
public class FeatureFlagConfig {

    private boolean validationEnabled = true;

}
