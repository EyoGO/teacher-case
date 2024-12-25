package com.eyogo.http.controller;

import com.eyogo.http.dto.CreateUserDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.exception.ValidationException;
import com.eyogo.http.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String registrationPage(Model model) {
        model.addAttribute("roles", Role.values());
        model.addAttribute("genders", Gender.values());

        return "registration";
    }

    @PostMapping
    public String register(Model model,
                           @ModelAttribute CreateUserDto userDto) {
        try {
            userService.create(userDto);
            return "redirect:/units";
        } catch (ValidationException exception) {
            model.addAttribute("errors", exception.getErrors());
            return registrationPage(model);
        }
    }
}
