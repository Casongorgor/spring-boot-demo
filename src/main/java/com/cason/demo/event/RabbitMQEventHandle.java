package com.cason.demo.event;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

/**
 * Rabbit MQ 监听
 */
@Component
public class RabbitMQEventHandle implements ChannelAwareMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMQEventHandle.class);


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            if (message == null || message.getBody().length == 0) {
                return;
            }
            String data = new String(message.getBody(), "utf-8");

            logger.info("RabbitMQ response data {}", data);
//            wedefendService.wedefendResponseProcessing(data);
        } catch (Exception e) {
            logger.error("Failed to accept rabbit mq data", e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}
