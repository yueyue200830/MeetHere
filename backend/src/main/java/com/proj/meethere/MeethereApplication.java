package com.proj.meethere;

import ch.qos.logback.classic.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class MeethereApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeethereApplication.class, args);
    }
}
