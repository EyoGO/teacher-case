package com.eyogo.http.service;

import com.eyogo.http.dao.UserRepository;
import com.eyogo.http.dto.CreateUserDto;
import com.eyogo.http.dto.GetUserDto;
import com.eyogo.http.entity.User;
import com.eyogo.http.exception.ValidationException;
import com.eyogo.http.mapper.CreateUserMapper;
import com.eyogo.http.mapper.GetUserMapper;
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

    private static final UserService INSTANCE = new UserService();

    @Autowired
    private UserRepository userRepository;
    private final GetUserMapper getUserMapper = GetUserMapper.getInstance();
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    public UserService() {
    }

    public Optional<GetUserDto> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(getUserMapper::mapFrom);
    }

    @SneakyThrows
    public Integer create(CreateUserDto userDto) {
        //validate
        ValidationResult validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        //map to entity
        User userEntity = createUserMapper.mapFrom(userDto);
        //saved entity to DAO
        imageService.upload(userEntity.getImage(), userDto.getImage().getInputStream());
        userRepository.save(userEntity);
        // return id
        return userEntity.getId();
    }

    public List<GetUserDto> findAll() {
        return userRepository.findAll().stream()
                .map(getUserMapper::mapFrom)
                .collect(Collectors.toList());
    }

    public Optional<GetUserDto> findById(Integer id) {
//        return userRepository.findById(id)
//                .map(getUserMapper::mapFrom);
//        return userRepository.findById(id);
        return null;
    }

    public List<UserNameProjection> findAllNames() {
        return userRepository.findAllNames();
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
