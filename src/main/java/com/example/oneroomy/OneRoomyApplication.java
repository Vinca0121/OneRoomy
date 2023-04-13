package com.example.oneroomy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class OneRoomyApplication {

    public static void main(String[] args) {
        SpringApplication.run(OneRoomyApplication.class, args);
    }

}
