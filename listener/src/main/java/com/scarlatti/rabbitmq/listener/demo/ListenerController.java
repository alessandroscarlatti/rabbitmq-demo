package com.scarlatti.rabbitmq.listener.demo;

import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ______    __                         __           ____             __     __  __  _
 * ___/ _ | / /__ ___ ___ ___ ____  ___/ /______    / __/______ _____/ /__ _/ /_/ /_(_)
 * __/ __ |/ / -_|_-<(_-</ _ `/ _ \/ _  / __/ _ \  _\ \/ __/ _ `/ __/ / _ `/ __/ __/ /
 * /_/ |_/_/\__/___/___/\_,_/_//_/\_,_/_/  \___/ /___/\__/\_,_/_/ /_/\_,_/\__/\__/_/
 * Saturday, 6/16/2018
 */
@RestController("/")
public class ListenerController {

    private RabbitListenerEndpointRegistry registry;

    public ListenerController(RabbitListenerEndpointRegistry registry) {
        this.registry = registry;
    }

    @PostMapping("start")
    ResponseEntity<String> start() {
        registry.getListenerContainer(BeanNames.RabbitListener).start();
        return ResponseEntity.ok("listening.");
    }

    @PostMapping("stop")
    ResponseEntity<String> stop() {
        registry.getListenerContainer(BeanNames.RabbitListener).stop();
        return ResponseEntity.ok("stopped.");
    }
}
