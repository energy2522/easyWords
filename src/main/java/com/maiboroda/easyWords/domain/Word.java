package com.maiboroda.easyWords.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
@Table
@Entity(name = "word")
public class Word {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "word", nullable = false)
    @NotBlank
    private String word;

    @Column(name = "language", nullable = false)
    @NotBlank
    private String language;

    @Column(name = "try_count", nullable = false)
    @Min(0)
    private int tryCount = 0;

    @Column(name = "rating", nullable = false)
    @Min(0)
    @Max(5)
    private double rating = 0;

    @Column(name = "last_try", nullable = false)
    private LocalDateTime lastTry = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(referencedColumnName = "id")
    private List<Translation> translations;
}
