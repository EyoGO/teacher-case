package com.eyogo.http.service;

import com.eyogo.http.dao.UserRepository;
import com.eyogo.http.dto.UserCreateDto;
import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.entity.User;
import com.eyogo.http.exception.ValidationException;
import com.eyogo.http.mapper.CreateUserMapper;
import com.eyogo.http.mapper.UserReadMapper;
import com.eyogo.http.projection.UserNameProjection;
import com.eyogo.http.validation.CreateUserValidator;
import com.eyogo.http.validation.ValidationResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserReadMapper userReadMapper;
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    public Optional<UserReadDto> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(userReadMapper::mapFrom);
    }

    @SneakyThrows
    public Integer create(UserCreateDto userDto) {
        //validate
        ValidationResult validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        //map to entity
        User userEntity = createUserMapper.mapFrom(userDto);
        //saved entity to DAO
//        imageService.upload(userEntity.getImage(), userDto.getImage().getInputStream()); // TODO image processing
        userRepository.save(userEntity);
        // return id
        return userEntity.getId();
    }

    public List<UserReadDto> findAll() {
        return userRepository.findAll().stream()
                .map(userReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public Optional<UserReadDto> findById(Integer id) {
        return userRepository.findById(id)
                .map(userReadMapper::mapFrom);
    }

    public List<UserNameProjection> findAllNames() {
        return userRepository.findAllNames();
    }
}
