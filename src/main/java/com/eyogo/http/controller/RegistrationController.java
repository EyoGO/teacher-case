package com.eyogo.http.controller;

import com.eyogo.http.dto.UserCreateDto;
import com.eyogo.http.entity.Gender;
import com.eyogo.http.entity.Role;
import com.eyogo.http.exception.ValidationException;
import com.eyogo.http.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
@Slf4j
public class RegistrationController {

    private final UserService userService;

    /* EXCEPTION HANDLING:
    The flow of handling errors is following:
    * 1. If there is BindingResult parameter in method, then it has precedence because it manually intervenes exception handling lifecycle.
    * 2. If there is @ExceptionHandler in Controller, then it is applied.
    * 3. If there is global @ExceptionHandler in class, marked as @ControllerAdvice, then global logic applied.
    Next take a look at @ControllerAdvice class - ControllerExceptionHandler*/
//    @ExceptionHandler(Exception.class)
//    public String handleValidationException(Exception ex, HttpServletRequest request) {
//        log.error("Failed to return response", ex);
//        return "error/error500";
//    }

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
                           @ModelAttribute @Valid UserCreateDto userDto,
//                           BindingResult bindingResult, // NOTE: only after validated form parameter, otherwise not working
                           RedirectAttributes redirectAttributes) {
        try {
//            if (bindingResult.hasErrors()) {
//                redirectAttributes.addFlashAttribute("user", userDto);
//                redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
//                return "redirect:/registration";
//            }
            userService.create(userDto);
            return "redirect:/units";
        } catch (ValidationException exception) {
            model.addAttribute("errors", exception.getErrors());
            return registrationPage(model, userDto);
        }
    }
}
