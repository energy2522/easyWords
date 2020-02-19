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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.maiboroda.easyWords.service.DateService;

import lombok.Data;

@Data
@Table
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", unique = true, nullable = false)
    @Size(min = 4)
    @NotBlank
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank
    private String password;

    @Column(name = "updated")
    @DateTimeFormat(pattern = DateService.DATE_TIME_FORMAT)
    private LocalDateTime updated = LocalDateTime.now();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn
    private List<Collection> collections;
}
