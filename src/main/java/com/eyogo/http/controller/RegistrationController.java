package com.eyogo.http.controller;

import com.eyogo.http.dto.UserCreateDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String registrationPage(Model model,
                                   @ModelAttribute("user") UserCreateDto userDto) {
        model.addAttribute("user", userDto);
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());

        return "registration";
    }

    @PostMapping
    public String register(@ModelAttribute @Valid UserCreateDto userDto) {
        userService.create(userDto);
        return "redirect:/units";
    }
}
