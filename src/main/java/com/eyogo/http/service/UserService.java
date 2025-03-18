package com.eyogo.http.service;

import com.eyogo.http.dto.UserCreateDto;
import com.eyogo.http.dto.UserReadDto;
import com.eyogo.http.mapper.CreateUserMapper;
import com.eyogo.http.mapper.UserReadMapper;
import com.eyogo.http.projection.UserNameProjection;
import com.eyogo.http.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final CreateUserMapper createUserMapper;
    private final ImageService imageService;

    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Transactional
    public UserReadDto create(UserCreateDto userDto) {
        return Optional.of(userDto)
                .map(dto -> {
                    uploadImage(dto.getImage());
                    return createUserMapper.mapFrom(dto);
                })
                .map(userRepository::save)
                .map(userReadMapper::mapFrom)
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(user -> new User(
                        user.getEmail(),
                        user.getPassword(),
                        Collections.singleton(user.getRole())
                )).orElseThrow(() -> new UsernameNotFoundException("Failed to retrieve user: " + username));
    }

    @Transactional
    public void updatePassword(com.eyogo.http.entity.User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.saveAndFlush(user);
    }
}
