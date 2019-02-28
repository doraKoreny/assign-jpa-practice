package com.koreny.dora.assignpracticejpa.repositories;

import com.koreny.dora.assignpracticejpa.entities.Episode;
import com.koreny.dora.assignpracticejpa.entities.Season;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class SeasonRepositoryTest {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Test
    public void saveSeason() {
        Season season = Season.builder()
                .name("Season 1")
                .releaseDate(LocalDate.of(1994, 9, 14))
                .build();

        seasonRepository.save(season);
        assertThat(seasonRepository.findAll()).hasSize(1);
    }

    @Test
    public void episodesArePersistentWithSeries() {
        Set<Episode> episodes = IntStream.range(1,11)
                .boxed()
                .map(integer -> Episode.builder().title("Episode " + integer).build())
                .collect(Collectors.toSet());

        Season season1 = Season.builder()
                .name("Season1")
                .episodes(episodes)
                .build();

        seasonRepository.save(season1);
        assertThat(episodeRepository.findAll())
                .hasSize(10)
                .anyMatch(episode -> episode.getTitle().equals("Episode 10"));
    }


}