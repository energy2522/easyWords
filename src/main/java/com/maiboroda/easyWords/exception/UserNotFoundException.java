package com.maiboroda.easyWords.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(int userId) {
        super("User with id " + userId + " not found");
    }
}
