package com.koreny.dora.assignpracticejpa.repositories;

import com.koreny.dora.assignpracticejpa.entities.Series;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
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



}