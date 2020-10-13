package com.gmail.aazavoykin.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app.mail")
public class MailProperties {

    private final Long resendPeriod = 60 * 60 * 1000L;

    private final Integer resendLimit = 5;

}
