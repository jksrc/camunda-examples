package bpm;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableProcessApplication("mySimpleApplication")
public class SimpleApplication {

    boolean processApplicationStopped;

    public static void main(final String... args) throws Exception {
        SpringApplication.run(SimpleApplication.class, args);
    }

}