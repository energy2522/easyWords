package com.maiboroda.easyWords.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CollectionDTO {

    @PositiveOrZero
    private int id;

    @NotBlank
    @Size(min = 2)
    private String name;

    private String description;
}
