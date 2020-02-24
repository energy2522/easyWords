package com.maiboroda.easyWords.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.maiboroda.easyWords.domain.Word;

public interface WordRepository extends CrudRepository<Word, Integer> {

    @Modifying
    @Query("update word w set w.language = :language, w.word = :word where w.id = :id")
    int update(int id, String word, String language);

    @Modifying
    @Query("update word w set w.tryCount = w.tryCount + 1, w.rating = :rating where w.id = :id")
    int updateProgress(int id, double rating);
}
