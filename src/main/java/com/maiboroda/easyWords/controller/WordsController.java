package com.maiboroda.easyWords.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.maiboroda.easyWords.dto.ProgressDTO;
import com.maiboroda.easyWords.dto.WordDTO;
import com.maiboroda.easyWords.service.WordService;

@RestController
public class WordsController {
    private WordService wordService;

    @GetMapping("/words/{id}")
    public @ResponseBody WordDTO getWord(@PathVariable("id") int id) {
        WordDTO wordDTO = wordService.get(id);

        return wordDTO;
    }

    @PutMapping("/words/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody WordDTO updateWord(@PathVariable("id") int id, @RequestBody WordDTO wordDTO) {
        WordDTO updatedWord = wordService.update(id, wordDTO);

        return updatedWord;
    }

    @GetMapping("/words/{id}/progress")
    public @ResponseBody ProgressDTO getProgress(@PathVariable("id") int id) {
        ProgressDTO progress = wordService.getProgress(id);

        return progress;
    }

    @PutMapping("/words/{id}/progress")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody ProgressDTO updateProgress(@PathVariable("id") int id, @RequestBody ProgressDTO progressDTO) {
        ProgressDTO progress = wordService.updateProgress(id, progressDTO);

        return progress;
    }

    @Autowired
    public void setWordService(WordService wordService) {
        this.wordService = wordService;
    }
}
