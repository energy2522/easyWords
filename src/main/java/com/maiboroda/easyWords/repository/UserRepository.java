package com.maiboroda.easyWords.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.maiboroda.easyWords.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Modifying
    @Query("UPDATE user u SET u.password = :password, u.updated = ?#{T(java.time.LocalDateTime).now()} WHERE u.id = :id")
    int updateUser(@Param("id") Integer id,  @Param("password") String password);
}
