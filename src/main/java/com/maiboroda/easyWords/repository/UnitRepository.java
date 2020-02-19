package com.maiboroda.easyWords.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.maiboroda.easyWords.domain.Unit;

public interface UnitRepository extends JpaRepository<Unit, Integer> {

    List<Unit> findAllByCollection_Id(int id);

    Unit findByCollection_IdAndWord_Id(int collectionId, int wordId);
}
