package com.maiboroda.easyWords.controller;

import java.util.List;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public @ResponseBody List<UserDTO> getUsers() {
        List<UserDTO> users = userService.getAll();

        return users;
    }

    @GetMapping("/users/{id}")
    public @ResponseBody UserDTO getUser(@PathVariable("id") int id) {
        UserDTO user = userService.getById(id);

        return user;
    }

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        UserDTO savedUser = userService.add(userDTO);

        return savedUser;
    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") @NotNull Integer id) {
        userService.delete(id);
    }

    @PutMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserDTO updateUser(@PathVariable("id") @NotNull Integer id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.update(id, userDTO);

        return updatedUser;
    }

}
