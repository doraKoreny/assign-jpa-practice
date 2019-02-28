package com.koreny.dora.assignpracticejpa.repositories;

import com.koreny.dora.assignpracticejpa.entities.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepositories extends JpaRepository<Series, Long> {
}
