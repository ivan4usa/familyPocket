package com.ivan4usa.fp.repositories;

import com.ivan4usa.fp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.password=:password WHERE u.email=:email")
    int updateUserPassword(@Param("password") String password, @Param("email") String email);

    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.name=:name, u.email=:email WHERE u.id=:id")
    int updateUserData(@Param("name") String name, @Param("email") String email, @Param("id") Long id);
}