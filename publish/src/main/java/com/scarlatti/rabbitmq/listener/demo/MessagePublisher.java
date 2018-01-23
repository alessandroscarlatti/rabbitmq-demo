package com.scarlatti.rabbitmq.listener.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 1/22/2018
 *
 * Spring will call this CommandLineRunner
 * once the application context is initialized.
 */
@Component
public class MessagePublisher implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private final static Logger log = LoggerFactory.getLogger(MessagePublisher.class);

    public MessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Write a message to the exchange built into the RabbitTemplate.
     * @param args program args (not used)
     */
    @Override
    public void run(String... args) {
        log.info("Publishing message...");
        Message message = MessageBuilder.withBody("Squawk!".getBytes()).build();
        rabbitTemplate.send(message);
        log.info("Message published.");
    }
}
