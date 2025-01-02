package com.eyogo.http.service;

import com.eyogo.http.dao.UserRepository;
import com.eyogo.http.dto.UserCreateDto;
import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.entity.User;
import com.eyogo.http.mapper.CreateUserMapper;
import com.eyogo.http.mapper.UserReadMapper;
import com.eyogo.http.projection.UserNameProjection;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final ImageService imageService;

    public Optional<UserReadDto> login(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .map(userReadMapper::mapFrom);
    }

    @SneakyThrows
    @Transactional
    public UserReadDto create(UserCreateDto userDto) {
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return createUserMapper.mapFrom(dto);
                })
                .map(entity -> userRepository.save(entity))
                .map(object -> userReadMapper.mapFrom(object))
                .orElseThrow();
    }

    @SneakyThrows
    private void uploadImage(MultipartFile image) {
        if (!image.isEmpty()) {
            imageService.upload(image.getOriginalFilename(), image.getInputStream());
        }
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
