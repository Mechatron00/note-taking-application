package com.notetaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.notetaking.repository"})
public class NoteTakingApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteTakingApplication.class, args);
        System.out.println("Note-taking Application works...");
    }

}
