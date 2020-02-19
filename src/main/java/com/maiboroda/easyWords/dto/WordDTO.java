package com.maiboroda.easyWords.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class WordDTO {

    @PositiveOrZero
    private int id;

    @NotBlank
    private String word;

    @NotBlank
    private String language;
}
