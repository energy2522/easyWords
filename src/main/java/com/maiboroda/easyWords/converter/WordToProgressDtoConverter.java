package com.maiboroda.easyWords.converter;

import org.springframework.core.convert.converter.Converter;

import com.maiboroda.easyWords.domain.Word;
import com.maiboroda.easyWords.dto.ProgressDTO;
import com.maiboroda.easyWords.service.DateService;

public class WordToProgressDtoConverter implements Converter<Word, ProgressDTO> {
    private DateService dateService;

    public WordToProgressDtoConverter(DateService dateService) {
        this.dateService = dateService;
    }

    @Override
    public ProgressDTO convert(Word word) {
        if (word == null) {
            return null;
        }
        ProgressDTO progressDTO = new ProgressDTO();
        progressDTO.setRating(word.getRating());
        progressDTO.setTryCount(word.getTryCount());
        progressDTO.setWordId(word.getId());
        progressDTO.setLastTry(dateService.formatDate(word.getLastTry()));

        return progressDTO;
    }
}
