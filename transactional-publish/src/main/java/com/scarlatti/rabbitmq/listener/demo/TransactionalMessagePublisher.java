package com.scarlatti.rabbitmq.listener.demo;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class TransactionalMessagePublisher implements CommandLineRunner {

    private RabbitTemplate rabbitTemplate;
    private final static Logger log = LoggerFactory.getLogger(TransactionalMessagePublisher.class);

    public TransactionalMessagePublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * Write a message to the exchange built into the RabbitTemplate.
     * @param args program args (not used)
     */
    @Override
    public void run(String... args) {
        log.info("Publishing five messages...");

        rabbitTemplate.execute(channel -> {
            try {
                channel.txSelect();         // begin a transaction
                publishMessages(channel);    // try to publish messages...
                channel.txCommit();         // ...would commit the transaction
            } catch (Exception e) {
                channel.txRollback();       // rollback this commit. Like it never even happened.
                log.error("Error publishing messages. Aborting transaction.", e);
            }

            return null;
        });
    }

    /**
     * Publish 5 messages, but then throw an exception.
     * @param channel the channel being used for this transaction.
     * @throws Exception on any exception thrown during publish.
     */
    private void publishMessages(Channel channel) throws Exception {

        for (int i = 0; i < 5; i++) {
            log.info("Publishing message " + (i + 1));
            channel.basicPublish(
                rabbitTemplate.getExchange(),
                rabbitTemplate.getRoutingKey(),
                new AMQP.BasicProperties.Builder().build(),
                "Squawks!".getBytes()
            );
        }

        throw new RuntimeException("Oh no! We'd better cancel this transaction!");
    }
}
