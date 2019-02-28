package com.koreny.dora.assignpracticejpa;

import com.koreny.dora.assignpracticejpa.entities.Genre;
import com.koreny.dora.assignpracticejpa.entities.Season;
import com.koreny.dora.assignpracticejpa.entities.Series;
import com.koreny.dora.assignpracticejpa.repositories.SeasonRepositories;
import com.koreny.dora.assignpracticejpa.repositories.SeriesRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.time.LocalDate;

@SpringBootApplication
public class AssignPracticeJpaApplication {

    @Autowired
    private SeriesRepositories seriesRepository;

    @Autowired
    private SeasonRepositories seasonRepository;

    public static void main(String[] args) {
        SpringApplication.run(AssignPracticeJpaApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            Season season1 = Season.builder()
                    .name("Season 1")
                    .releaseDate(LocalDate.of(1994, 9, 14))
                    .build();

            Season season2 = Season.builder()
                    .name("Season 2")
                    .releaseDate(LocalDate.of(1995, 9, 21))
                    .build();

            Series friends = Series.builder()
                    .name("Friends")
                    .genre(Genre.COMEDY)
                    .releaseDate(LocalDate.of(1994,9,14))
                    .season(season1)
                    .season(season2)
                    .build();
            friends.calculateNumberOfSeasons();


            season1.setSeries(friends);
            season2.setSeries(friends);
            seriesRepository.save(friends);

        };
    }
}
