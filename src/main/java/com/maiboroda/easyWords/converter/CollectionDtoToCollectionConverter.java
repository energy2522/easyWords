package com.maiboroda.easyWords.converter;

import org.springframework.core.convert.converter.Converter;

import com.maiboroda.easyWords.domain.Collection;
import com.maiboroda.easyWords.dto.CollectionDTO;

public class CollectionDtoToCollectionConverter implements Converter<CollectionDTO, Collection> {
    @Override
    public Collection convert(CollectionDTO collectionDTO) {
        if (collectionDTO == null) {
            return null;
        }

        Collection collection = new Collection();
        collection.setId(collectionDTO.getId());
        collection.setName(collectionDTO.getName());
        collection.setDescription(collectionDTO.getDescription());

        return collection;
    }
}
