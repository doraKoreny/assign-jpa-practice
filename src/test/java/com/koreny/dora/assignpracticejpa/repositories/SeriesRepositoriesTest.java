package com.koreny.dora.assignpracticejpa.repositories;

import com.koreny.dora.assignpracticejpa.entities.Episode;
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
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.in;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class SeriesRepositoriesTest {

    @Autowired
    private SeriesRepository seriesRepository;

    @Autowired
    private SeasonRepository seasonRepository;

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void saveOneSimple() {
        Series friends = Series.builder()
                .name("Friends")
                .releaseDate(LocalDate.of(1994, 9, 22))
                .build();

        seriesRepository.save(friends);

        List<Series> seriesList = seriesRepository.findAll();
        assertThat(seriesList).hasSize(1);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void seriesNameShouldNotBeNull() {
        Series series = Series.builder()
                .releaseDate(LocalDate.of(1994, 9, 14))
                .build();

        seriesRepository.save(series);
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

        seriesRepository.save(friends);
        testEntityManager.clear();

        List<Series> series = seriesRepository.findAll();
        assertThat(series).allMatch(series1 -> series1.getNumberOfSeasons() == 0);
    }

    @Test
    public void seasonsArePersistentWithSeries() {
        Set<Season> seasons = IntStream.range(1,11)
                .boxed()
                .map(integer -> Season.builder().name("Season" + integer).build())
                .collect(Collectors.toSet());

        Series friends = Series.builder()
                .name("Friends")
                .releaseDate(LocalDate.of(1994,9,14))
                .genre(Genre.COMEDY)
                .seasons(seasons)
                .build();

        seriesRepository.save(friends);

        assertThat(seasonRepository.findAll())
                .hasSize(10)
                .anyMatch(season -> season.getName().equals("Season10"));
    }

    @Test
    public void seasonsAreDeletedWithSeries() {
        Set<Season> seasons = IntStream.range(1,11)
                .boxed()
                .map(integer -> Season.builder().name("Season" + integer).build())
                .collect(Collectors.toSet());

        Series friends = Series.builder()
                .name("Friends")
                .seasons(seasons)
                .build();

        seriesRepository.save(friends);

        seriesRepository.deleteAll();
        assertThat(seasonRepository.findAll())
                .hasSize(0);
    }

    @Test
    public void episodesAndSeasonsAreDeletedWithSeries() {
        Set<Episode> episodes = IntStream.range(1,10)
                .boxed()
                .map(integer -> Episode.builder().title("Episode" + integer).build())
                .collect(Collectors.toSet());

        Season season1 = Season.builder()
                .name("Season1")
                .episodes(episodes)
                .build();

        Series friends = Series.builder()
                .season(season1)
                .name("Friends")
                .build();

        season1.setSeries(friends);
        seriesRepository.save(friends);

        assertThat(episodeRepository.findAll())
                .hasSize(9)
                .anyMatch(episode -> episode.getTitle().equals("Episode9"));

        seriesRepository.deleteAll();
        assertThat(episodeRepository.findAll()).hasSize(0);
    }
}