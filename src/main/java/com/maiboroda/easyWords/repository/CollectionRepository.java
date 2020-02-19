package com.maiboroda.easyWords.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.maiboroda.easyWords.domain.Collection;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    @Modifying
    @Query("update collection c set c.name = :name, c.description = :description, c.updated = ?#{T(java.time.LocalDateTime).now()} where c.id = :id")
    int update(@Param("id") int id, @Param("name") String name, @Param("description") String description);
}
