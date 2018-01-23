package com.scarlatti.rabbitmq.multipleListeners.demo;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
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
     * Configure the North Pole Rabbit listener.
     * Specifying bean as @Primary only to satisfy RabbitAutoConfiguration.
     */

    @Primary
    @Bean("northPoleConnectionFactory")
    @ConfigurationProperties("rabbitmq.northPole.connection")
    public ConnectionFactory northPoleConnectionFactory() {
        return new CachingConnectionFactory();
    }

    @Bean("northPoleListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory northPoleListenerContainerFactory(
        @Qualifier("northPoleConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        // you can define a heartbeat, etc. here...

        return factory;
    }

    /**
     * Now configure the South Pole Rabbit listener.
     */

    @Bean("southPoleConnectionFactory")
    @ConfigurationProperties("rabbitmq.southPole.connection")
    public ConnectionFactory southPoleconnectionFactory() {
        return new CachingConnectionFactory();
    }

    @Bean(name = "southPoleListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory southPoleListenerContainerFactory(
        @Qualifier("southPoleConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);

        // you can define a heartbeat, etc. here...

        return factory;
    }
}
