package com.eyogo.http.dao;

import com.eyogo.http.dto.GetUserDto;
import com.eyogo.http.dto.GetUserDto2;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.entity.User;
import com.eyogo.http.util.ConnectionManager;
import lombok.SneakyThrows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Envers->
// Since it is convenient to work on users with single UserRepository,
// and we do not want to go deep to entity manager to query revision and user_aud tables,
// we can extend our interface from RevisionRepository<Entity, id type, revision id type> and get all ready.
public interface UserRepository extends
        JpaRepository<User, Integer>,
        RevisionRepository<User, Integer, Integer> {

    public static final String SAVE_SQL = "INSERT INTO users (first_name, last_name, email, password, role, birthday, image, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String GET_BY_EMAIL_AND_PASSWORD_SQL = "SELECT * FROM users WHERE email = ? AND password = ?";

    List<User> findAll();

    Optional<User> findById(Integer id);

    User save(User entity);

    Optional<User> findByEmailAndPassword(String email, String password);

    // Projections select only requested by DTO data:
//    Optional<GetUserDto> findById(Integer id);

    // Generic Projection using - now we can fetch any DTO by role (but using DTO as classes is not allowed for native queries, it is allowed with interface DTO:)
    <T> List<T> findAllByRole(Role role, Class<T> clazz);

    // Projection with native query (interface DTO)
    // Main point here is that selected fields must have corresponding names or aliases to interface DTO
    // For example getBirthDate() returns null until we add correct alias
    // This means we can add calculated operations (need to have calculation field in DTO) like count...
    // Moreover, we can create own calculated fields like Full Name in GetUserDto2
    @Query(value = "SELECT first_name, " +
                   "last_name, " +
                   "email, " +
                   "birthday as birthDate " +
                   "FROM {h-schema}users " +
                   "WHERE role = :#{#role.name()}",
            nativeQuery = true)
    List<GetUserDto2> findAllByRole(Role role);

    // When we need to add custom repository, then we create new interface FilterRepository (with findByFilter(Filter f) declaration),
    // then we create FilterRepositoryImpl (Impl postfix is crucial and will contain EntityManager and some Criteria API filtering) implementation.
    // Then we extend this UserRepository from FilterRepository and now spring will find the Impl class and add implementations
    // to this UserRepository
}
