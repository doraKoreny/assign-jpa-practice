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
public class Series {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Singular
    @OneToMany(mappedBy = "series",cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    private Set<Season> seasons;

    @Transient
    private int numberOfSeasons;

    public void calculateNumberOfSeasons() {
        numberOfSeasons = seasons.size();
    }

}
