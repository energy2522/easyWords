package com.maiboroda.easyWords.converter;

import org.springframework.core.convert.converter.Converter;

import com.maiboroda.easyWords.domain.Word;
import com.maiboroda.easyWords.dto.WordDTO;

public class WordToWordDtoConverter implements Converter<Word, WordDTO> {
    @Override
    public WordDTO convert(Word word) {
        if (word == null) {
            return null;
        }
        WordDTO wordDTO = new WordDTO();
        wordDTO.setWord(word.getWord());
        wordDTO.setId(word.getId());
        wordDTO.setLanguage(word.getLanguage());

        return wordDTO;
    }
}
