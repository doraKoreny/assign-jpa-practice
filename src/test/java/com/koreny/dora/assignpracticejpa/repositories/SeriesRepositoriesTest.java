package com.koreny.dora.assignpracticejpa.repositories;

import com.koreny.dora.assignpracticejpa.entities.Genre;
import com.koreny.dora.assignpracticejpa.entities.Season;
import com.koreny.dora.assignpracticejpa.entities.Series;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class SeriesRepositoriesTest {

    @Autowired
    private SeriesRepositories seriesRepositories;

    @Autowired
    private SeasonRepositories seasonRepositories;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void saveOneSimple() {
        Series friends = Series.builder()
                .name("Friends")
                .releaseDate(LocalDate.of(1994, 9, 22))
                .build();

        seriesRepositories.save(friends);

        List<Series> seriesList = seriesRepositories.findAll();
        assertThat(seriesList).hasSize(1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void seriesNameShouldNotBeNull() {
        Series series = Series.builder()
                .releaseDate(LocalDate.of(1994, 9, 14))
                .build();

        seriesRepositories.save(series);
    }

    @Test
    public void transientIsNotSaved() {
        Season season1 = Season.builder()
                .name("season 1")
                .build();

        Season season2 = Season.builder()
                .name("season 2")
                .build();

        Series friends = Series.builder()
                .name("Friends")
                .releaseDate(LocalDate.of(1994, 9, 14))
                .genre(Genre.COMEDY)
                .season(season1)
                .season(season2)
                .build();
        friends.calculateNumberOfSeasons();
        assertThat(friends.getNumberOfSeasons()).isGreaterThanOrEqualTo(2);

        seriesRepositories.save(friends);
        testEntityManager.clear();

        List<Series> series = seriesRepositories.findAll();
        assertThat(series).allMatch(series1 -> series1.getNumberOfSeasons() == 0);
    }
}