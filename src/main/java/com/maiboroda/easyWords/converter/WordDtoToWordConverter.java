package com.maiboroda.easyWords.converter;

import org.springframework.core.convert.converter.Converter;

import com.maiboroda.easyWords.domain.Word;
import com.maiboroda.easyWords.dto.WordDTO;

public class WordDtoToWordConverter implements Converter<WordDTO, Word> {
    @Override
    public Word convert(WordDTO wordDTO) {
        if (wordDTO == null) {
            return null;
        }
        Word word = new Word();
        word.setLanguage(wordDTO.getLanguage());
        word.setWord(wordDTO.getWord());

        return word;
    }
}
