package com.scarlatti.rabbitmq.listener.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 1/22/2018
 */
@Component
public class MessageConsumer {

    private final static Logger log = LoggerFactory.getLogger(MessageConsumer.class);

    @RabbitListener(queues = "${rabbitmq.consumer.queue}", id = BeanNames.RabbitListener)
    public void receiveMessage(byte[] bytes) {
        log.info("received message: " + new String(bytes));
    }
}
