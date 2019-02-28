package com.koreny.dora.assignpracticejpa;

import com.koreny.dora.assignpracticejpa.entities.Series;
import com.koreny.dora.assignpracticejpa.repositories.SeriesRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class AssignPracticeJpaApplication {

    @Autowired
    private SeriesRepositories seriesRepositories;

    public static void main(String[] args) {
        SpringApplication.run(AssignPracticeJpaApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            Series friends = Series.builder()
                    .name("Friends")
                    .build();

            seriesRepositories.save(friends);
        };
    }
}
