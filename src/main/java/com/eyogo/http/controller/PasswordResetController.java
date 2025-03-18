package com.eyogo.http.controller;

import com.eyogo.http.entity.PasswordResetToken;
import com.eyogo.http.service.PasswordResetService;
import com.eyogo.http.service.UserService;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;
    private final UserService userService;

    @GetMapping
    public String resetPasswordPage(Model model, @RequestParam(required = false) String token) {
        if (token == null) {
            return "password-reset";
        }
        Optional<PasswordResetToken> token1 = passwordResetService.getToken(token);
        if (token1.isPresent()) {
            if (token1.get().getExpiryDate().isBefore(LocalDateTime.now())) {
                return "redirect:/login?expired";
            }
            model.addAttribute("token", token);
            return "new-password";
        }
        return "redirect:/login";
    }

    @PostMapping
    public String resetPassword(@Validated @Email @RequestParam String email) {
        boolean messageSent = passwordResetService.generateResetTokenAndEmail(email);
        return "redirect:/login?" + (messageSent ? "reset" : "error");
    }

    @PostMapping
    @RequestMapping("/update-password")
    public String resetPassword(@RequestParam String password,
                                @RequestParam String token) {
        PasswordResetToken passwordResetToken = passwordResetService.getToken(token)
                .orElseThrow();
        if (passwordResetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Password reset token expired");
        }
        userService.updatePassword(passwordResetToken.getUser(), password);
        return "redirect:/login";
    }
}
