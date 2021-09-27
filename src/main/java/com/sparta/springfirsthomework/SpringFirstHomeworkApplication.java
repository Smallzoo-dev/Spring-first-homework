package com.sparta.springfirsthomework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringFirstHomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFirstHomeworkApplication.class, args);
    }

}
