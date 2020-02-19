package com.maiboroda.easyWords.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class ProgressDTO {

    @PositiveOrZero
    private int wordId;

    @PositiveOrZero
    private int tryCount;

    @PositiveOrZero
    private double rating;

    private String lastTry;
}
