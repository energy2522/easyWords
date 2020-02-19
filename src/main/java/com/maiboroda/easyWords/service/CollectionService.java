package com.maiboroda.easyWords.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiboroda.easyWords.domain.Collection;
import com.maiboroda.easyWords.domain.Unit;
import com.maiboroda.easyWords.dto.CollectionDTO;
import com.maiboroda.easyWords.dto.WordDTO;
import com.maiboroda.easyWords.exception.CollectionNotFoundException;
import com.maiboroda.easyWords.repository.CollectionRepository;
import com.maiboroda.easyWords.repository.UnitRepository;
import com.maiboroda.easyWords.repository.WordRepository;

@Service
public class CollectionService {
    private CollectionRepository collectionRepository;
    private ConversionService conversionService;
    private UnitRepository unitRepository;
    private WordRepository wordRepository;

    public List<CollectionDTO> getAll() {
        List<Collection> collections = collectionRepository.findAll();

        return collections.stream().map(col -> conversionService.convert(col, CollectionDTO.class)).collect(Collectors.toList());
    }

    public CollectionDTO getById(int id) {
        Collection collection = collectionRepository.findById(id).orElseThrow(() -> new CollectionNotFoundException(id));

        return conversionService.convert(collection, CollectionDTO.class);
    }

    @Transactional
    public CollectionDTO add(CollectionDTO collectionDTO) {
        Collection collection = conversionService.convert(collectionDTO, Collection.class);

        Collection savedCollection = collectionRepository.save(collection);

        return conversionService.convert(savedCollection, CollectionDTO.class);
    }

    @Transactional
    public CollectionDTO update(int id, CollectionDTO collectionDTO) {
        collectionRepository.update(id, collectionDTO.getName(), collectionDTO.getDescription());

        return conversionService.convert(collectionRepository.findById(id).get(), CollectionDTO.class);
    }

    @Transactional
    public void delete(int id) {
        List<Unit> units = unitRepository.findAllByCollection_Id(id);

        for (Unit unit : units) {
            unitRepository.deleteById(unit.getId());
            wordRepository.deleteById(unit.getWord().getId());
        }

        collectionRepository.deleteById(id);
    }

    public List<WordDTO> getWords(int collectionId) {
        List<Unit> units = unitRepository.findAllByCollection_Id(collectionId);

        return units.stream().map(Unit::getWord).map(word -> conversionService.convert(word, WordDTO.class)).collect(Collectors.toList());
    }

    @Autowired
    public void setCollectionRepository(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @Autowired
    public void setConversionService(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Autowired
    public void setUnitRepository(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @Autowired
    public void setWordRepository(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }
}
