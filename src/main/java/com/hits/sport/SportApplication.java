package com.hits.sport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SportApplication.class, args);
    }

}
