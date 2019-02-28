package com.koreny.dora.assignpracticejpa.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Season {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private LocalDate releaseDate;

    @ManyToOne
    private Series series;


}
