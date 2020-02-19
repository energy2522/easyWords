package com.maiboroda.easyWords.converter;

import org.springframework.core.convert.converter.Converter;

import com.maiboroda.easyWords.domain.Collection;
import com.maiboroda.easyWords.dto.CollectionDTO;

public class CollectionToCollectionDtoConverter implements Converter<Collection, CollectionDTO> {
    @Override
    public CollectionDTO convert(Collection collection) {
        if (collection == null) {
            return null;
        }

        CollectionDTO collectionDTO = new CollectionDTO();
        collectionDTO.setId(collection.getId());
        collectionDTO.setDescription(collection.getDescription());
        collectionDTO.setName(collection.getName());

        return collectionDTO;
    }
}
