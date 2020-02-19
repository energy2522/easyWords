package com.maiboroda.easyWords.exception;

public class CollectionNotFoundException extends RuntimeException {

    public CollectionNotFoundException(int collectionId) {

        super("Collection with id " + collectionId + " not found");
    }
}
