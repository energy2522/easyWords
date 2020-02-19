package com.maiboroda.easyWords.exception;

public class WordNotFoundException extends RuntimeException {

    public WordNotFoundException(int wordId) {
        super("Word with id " + wordId + " not found");
    }
}
