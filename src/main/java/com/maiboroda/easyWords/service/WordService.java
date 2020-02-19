package com.maiboroda.easyWords.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiboroda.easyWords.domain.Collection;
import com.maiboroda.easyWords.domain.Unit;
import com.maiboroda.easyWords.domain.Word;
import com.maiboroda.easyWords.dto.CollectionDTO;
import com.maiboroda.easyWords.dto.ProgressDTO;
import com.maiboroda.easyWords.dto.WordDTO;
import com.maiboroda.easyWords.repository.UnitRepository;
import com.maiboroda.easyWords.repository.WordRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class WordService {
    private WordRepository wordRepository;
    private UnitRepository unitRepository;
    private ConversionService conversionService;

    public WordDTO get(int id) {
        Word word = wordRepository.findById(id).orElse(null);

        return conversionService.convert(word, WordDTO.class);
    }

    @Transactional
    public WordDTO addWord(CollectionDTO collectionDTO, WordDTO wordDTO) {
        Word word = conversionService.convert(wordDTO, Word.class);

        Collection collection = conversionService.convert(collectionDTO, Collection.class);
        Word savedWord = wordRepository.save(word);

        Unit unit = new Unit();
        unit.setCollection(collection);
        unit.setWord(savedWord);

        unitRepository.save(unit);

        return conversionService.convert(savedWord, WordDTO.class);
    }

    @Transactional
    public void delete(int collectionId, int wordId) {
        Unit unit = unitRepository.findByCollection_IdAndWord_Id(collectionId, wordId);
        if (unit != null) {
            unitRepository.deleteById(unit.getId());
        } else {
            log.info("Object 'unit' not found for collection {} and word {}", collectionId, wordId);
        }

        wordRepository.deleteById(wordId);
    }

    @Transactional
    public WordDTO update(int id, WordDTO wordDTO) {
        wordRepository.update(id, wordDTO.getWord(), wordDTO.getLanguage());

        return conversionService.convert(wordRepository.findById(id).get(), WordDTO.class);
    }

    @Autowired
    public void setWordRepository(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Autowired
    public void setUnitRepository(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Autowired
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    public ProgressDTO getProgress(int id) {
        Word word = wordRepository.findById(id).get();

        return conversionService.convert(word, ProgressDTO.class);
    }

    public ProgressDTO updateProgress(int id, ProgressDTO progressDTO) {
        Word word = wordRepository.findById(id).orElseThrow(() -> );

        wordRepository.updateProgress(id, )
        return null;
    }
}
