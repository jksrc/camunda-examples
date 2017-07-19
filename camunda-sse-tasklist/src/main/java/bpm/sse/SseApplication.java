package bpm.sse;

import javax.annotation.PostConstruct;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import bpm.sse.plugins.tasknotifications.DefaultParsersPlugin;
import bpm.sse.rest.ServerSentEventsResource;
import bpm.sse.rest.SseTasklistNotifier;

@SpringBootApplication
@EnableScheduling
@EnableProcessApplication("mySimpleApplication")
public class SseApplication {

    boolean processApplicationStopped;

    public static void main(final String... args) throws Exception {
        SpringApplication.run(SseApplication.class, args);
    }

    @Autowired
    ResourceConfig rc;

    @PostConstruct
    public void setupResources() {
        rc.register(ServerSentEventsResource.class);
    }

    @Bean
    public SseTasklistNotifier sseTasklistNotifier() {
        return new SseTasklistNotifier();
    }

    @Bean
    public DefaultParsersPlugin DefaultParsersPlugin() {
        return new DefaultParsersPlugin(null, sseTasklistNotifier());
    }

    @Bean
    public bpm.sse.StartListener StartListener() {
        return new bpm.sse.StartListener();
    }

}