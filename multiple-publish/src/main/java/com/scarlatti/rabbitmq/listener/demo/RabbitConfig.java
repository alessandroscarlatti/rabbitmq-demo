package com.scarlatti.rabbitmq.listener.demo;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Monday, 1/22/2018
 */
@Configuration
public class RabbitConfig {

    /**
     * Configure the North Pole RabbitTemplate.
     * Must use @Primary if you desire to use
     * Spring auto-configuration.
     */

    @Primary
    @Bean("northPoleConnectionFactory")
    @ConfigurationProperties("rabbitmq.northPole.connection")
    public ConnectionFactory northPoleConnectionFactory() {
        return new CachingConnectionFactory();
    }

    @Bean("northPoleRabbitTemplate")
    @ConfigurationProperties("rabbitmq.northPole.publisher")
    public RabbitTemplate northPoleRabbitTemplate(
        @Qualifier("northPoleConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    /**
     * Now configure the South Pole RabbitTemplate.
     */

    @Bean("southPoleConnectionFactory")
    @ConfigurationProperties("rabbitmq.southPole.connection")
    public ConnectionFactory southPoleConnectionFactory() {
        return new CachingConnectionFactory();
    }

    @Bean("southPoleRabbitTemplate")
    @ConfigurationProperties("rabbitmq.southPole.publisher")
    public RabbitTemplate southPoleRabbitTemplate(
        @Qualifier("southPoleConnectionFactory") ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
