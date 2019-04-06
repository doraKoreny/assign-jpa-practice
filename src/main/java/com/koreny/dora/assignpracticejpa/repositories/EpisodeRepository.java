package com.koreny.dora.assignpracticejpa.repositories;

import com.koreny.dora.assignpracticejpa.entities.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
}
