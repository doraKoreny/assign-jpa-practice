package com.koreny.dora.assignpracticejpa.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

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
