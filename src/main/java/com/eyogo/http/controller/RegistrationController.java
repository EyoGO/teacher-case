package com.eyogo.http.controller;

import com.eyogo.http.dto.UserCreateDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
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
    //                redirectAttributes.addAttribute("firstName", userDto.getFirstName());
//                redirectAttributes.addAttribute("lastName", userDto.getLastName());
//                redirectAttributes.addAttribute("email", userDto.getEmail());
//                return "redirect:/registration?firstName=" + userDto.getFirstName() + "&lastName=" + userDto.getLastName();
    @PostMapping
    public String register(Model model,
                           @ModelAttribute UserCreateDto userDto,
                           RedirectAttributes redirectAttributes) {
        try {
            if (true) {
                redirectAttributes.addFlashAttribute("user", userDto);
                return "redirect:/registration";
            }
            userService.create(userDto);
            return "redirect:/units";
        } catch (ValidationException exception) {
            model.addAttribute("errors", exception.getErrors());
            return registrationPage(model, userDto);
        }
    }
}
