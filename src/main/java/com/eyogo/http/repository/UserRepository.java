package com.eyogo.http.repository;

import com.eyogo.http.entity.User;
import com.eyogo.http.projection.UserNameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends
        JpaRepository<User, Integer>,
        RevisionRepository<User, Integer, Integer> {

    List<User> findAll();

    Optional<User> findById(Integer id);

    @Query("""
            select
                u.id as id,
                u.firstName as firstName,
                u.lastName as lastName
            FROM User u
            """)
    List<UserNameProjection> findAllNames();

    Optional<User> findByEmail(String email);

}
