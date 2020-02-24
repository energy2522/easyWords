package com.maiboroda.easyWords.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    @PositiveOrZero
    private int id;

    @NotBlank(message = "username {validation.NotBlank.message}")
    @Size(min = 4, message = "username {validation.Size.message}")
    @Pattern(regexp = "[a-zA-z0-9]+", message = "username {validation.Pattern.message}")
    private String username;

    @NotBlank(message = "password {validation.NotBlank.message}")
    @Size(min = 6, message = "password {validation.Size.message}")
    private String password;

    private String updated;
}
