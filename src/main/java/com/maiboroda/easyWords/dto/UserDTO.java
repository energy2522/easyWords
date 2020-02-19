package com.maiboroda.easyWords.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDTO {

    @PositiveOrZero
    private int id;

    @NotBlank
    @Size(min = 4)
    private String username;

    @Size(min = 6)
    private String password;

    private String updated;
}
