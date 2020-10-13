package com.gmail.aazavoykin;

import com.gmail.aazavoykin.config.property.MailProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableConfigurationProperties(MailProperties.class)
public class CommonConfiguration {


}
