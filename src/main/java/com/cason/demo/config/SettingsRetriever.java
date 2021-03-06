package com.cason.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by jingle.huang on 2017/3/8.
 */
@Component
@ConfigurationProperties
public class SettingsRetriever {

    @Value("${application.message}")
    private String message = "hi,hello world......";

    @Value("${mq.event.rabbitmq.consumer.key}")
    private String senderRoutingKey;

    public String getMessage() {
        return message;
    }

    public String getSenderRoutingKey() {
        return senderRoutingKey;
    }
}
