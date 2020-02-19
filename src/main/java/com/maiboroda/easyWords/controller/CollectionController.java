package com.maiboroda.easyWords.controller;

import java.util.List;

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

import com.maiboroda.easyWords.dto.CollectionDTO;
import com.maiboroda.easyWords.dto.WordDTO;
import com.maiboroda.easyWords.service.CollectionService;
import com.maiboroda.easyWords.service.WordService;

@RestController
public class CollectionController {
    private CollectionService collectionService;
    private WordService wordService;

    public CollectionController(CollectionService collectionService, WordService wordService) {
        this.collectionService = collectionService;
        this.wordService = wordService;
    }

    @GetMapping("/collections")
    public @ResponseBody List<CollectionDTO> getCollections() {
        List<CollectionDTO> collections = collectionService.getAll();

        return collections;
    }

    @GetMapping("/collections/{id}")
    public @ResponseBody CollectionDTO getCollection(@PathVariable("id") int id) {
        CollectionDTO collection = collectionService.getById(id);

        return collection;
    }

    @PostMapping("/collections")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody CollectionDTO addCollection(@RequestBody CollectionDTO collection) {
        CollectionDTO savedCollection = collectionService.add(collection);

        return savedCollection;
    }

    @PutMapping("/collections/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody CollectionDTO updateCollection(@PathVariable("id") int id, @RequestBody CollectionDTO collection) {
        CollectionDTO updatedCollection = collectionService.update(id, collection);

        return updatedCollection;
    }

    @DeleteMapping("/collections/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteCollection(@PathVariable("id") int id) {
        collectionService.delete(id);
    }

    @GetMapping("/collections/{id}/words")
    public @ResponseBody List<WordDTO> getWords(@PathVariable("id") int id) {
        return collectionService.getWords(id);
    }

//    @GetMapping("/collections/{collectionId}/words/{wordId}")
//    public @ResponseBody WordDTO getWord(@PathVariable("collectionId") int collectionId, @PathVariable("wordId" ) int wordId) {
//        List<WordDTO> words = collectionService.getWords(collectionId);
//
//        return words.stream().filter(word -> wordId == word.getId()).findFirst().orElse(null);
//    }

    @PostMapping("/collections/{id}/words")
    @ResponseStatus(code = HttpStatus.CREATED)
    public @ResponseBody WordDTO addWord(@PathVariable("id") int id, @RequestBody WordDTO wordDTO) {
        CollectionDTO collectionDTO = collectionService.getById(id);
        WordDTO savedWord = wordService.addWord(collectionDTO, wordDTO);

        return savedWord;
    }

    @PutMapping("/collections/{collectionId}/words/{wordId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteWord(@PathVariable("collectionId") int collectionId, @PathVariable("wordId" ) int wordId) {
        wordService.delete(collectionId, wordId);
    }
 }
