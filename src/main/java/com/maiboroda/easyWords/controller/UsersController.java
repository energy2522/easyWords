package com.maiboroda.easyWords.controller;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maiboroda.easyWords.dto.UserDTO;
import com.maiboroda.easyWords.service.UserService;

@RestController
@Validated
public class UsersController {

    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public @ResponseBody List<UserDTO> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/users/{userId}")
    public @ResponseBody UserDTO getUser(@PathVariable("userId") @Min(value = 1, message = "userId {validation.Min.message}") int id) {
        return userService.getById(id);
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO addUser(@Valid @RequestBody UserDTO userDTO) {
        return userService.add(userDTO);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("userId") @Min(value = 1, message = "userId {validation.Min.message}") int id) {
        userService.delete(id);
    }

    @PutMapping("/users/{userId}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserDTO updateUser(@PathVariable("userId") @Min(value = 1, message = "userId {validation.Min.message}") int id,
            @Validated @RequestBody UserDTO userDTO) {
        return userService.update(id, userDTO);
    }

}
