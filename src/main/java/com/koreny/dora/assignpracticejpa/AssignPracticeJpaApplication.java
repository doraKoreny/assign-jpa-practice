package com.koreny.dora.assignpracticejpa;

import com.koreny.dora.assignpracticejpa.entities.Episode;
import com.koreny.dora.assignpracticejpa.entities.Genre;
import com.koreny.dora.assignpracticejpa.entities.Season;
import com.koreny.dora.assignpracticejpa.entities.Series;
import com.koreny.dora.assignpracticejpa.repositories.SeasonRepository;
import com.koreny.dora.assignpracticejpa.repositories.SeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootApplication
public class AssignPracticeJpaApplication {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    public static void main(String[] args) {
        SpringApplication.run(AssignPracticeJpaApplication.class, args);
    }

    @Bean
    @Profile("production")
    public CommandLineRunner init() {
        return args -> {
            Episode episode1 = Episode.builder()
                    .title("The One Where Monica Gets a Roommate")
                    .length(LocalTime.of(0, 22))
                    .build();

            Episode episode2 = Episode.builder()
                    .title("The One with the Sonogram at the End")
                    .length(LocalTime.of(0, 22))
                    .build();

            Episode episode3 = Episode.builder()
                    .title("The One with the blabla")
                    .length(LocalTime.of(0, 22))
                    .build();

            Episode episode4 = Episode.builder()
                    .title("The One with the Christmas Tatu")
                    .length(LocalTime.of(0, 22))
                    .build();

            Season season1 = Season.builder()
                    .name("Season 1")
                    .releaseDate(LocalDate.of(1994, 9, 14))
                    .episode(episode1)
                    .episode(episode2)
                    .build();

            Season season2 = Season.builder()
                    .name("Season 2")
                    .releaseDate(LocalDate.of(1995, 9, 21))
                    .episode(episode3)
                    .build();

            Season season3 = Season.builder()
                    .name("Season 3")
                    .releaseDate(LocalDate.of(1996, 9, 21))
                    .episode(episode4)
                    .build();

            Season season4 = Season.builder()
                    .name("Season 4")
                    .releaseDate(LocalDate.of(1997, 9, 21))
                    .build();

            Season season5 = Season.builder()
                    .name("Season 5")
                    .releaseDate(LocalDate.of(1998, 9, 21))
                    .build();

            Series friends = Series.builder()
                    .name("Friends")
                    .genre(Genre.COMEDY)
                    .releaseDate(LocalDate.of(1994,9,14))
                    .season(season1)
                    .season(season2)
                    .season(season3)
                    .season(season4)
                    .season(season5)
                    .actor("Jennifer Aniston")
                    .actor("David Schwimmer")
                    .actor("Matt LeBlanc")
                    .actor("Matthew Perry")
                    .actor("Lisa Kudrow")
                    .actor("Courtney Cox Arquette")
                    .build();
            friends.calculateNumberOfSeasons();

            season1.setSeries(friends);
            season2.setSeries(friends);

            episode1.setSeason(season1);
            episode2.setSeason(season1);
            episode3.setSeason(season2);
            episode4.setSeason(season2);

            seriesRepository.save(friends);

        };
    }
}
