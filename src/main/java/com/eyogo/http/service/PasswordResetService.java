package com.eyogo.http.service;

import com.eyogo.http.repository.PasswordResetTokenRepository;
import com.eyogo.http.repository.UserRepository;
import com.eyogo.http.entity.PasswordResetToken;
import com.eyogo.http.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final JavaMailSender mailSender;

    public boolean findToken(String token) {
        return passwordResetTokenRepository.findByToken(token).isPresent();
    }

    @Transactional
    public boolean generateResetTokenAndEmail(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = UUID.randomUUID().toString();
            PasswordResetToken passwordResetToken = new PasswordResetToken(token, user, LocalDateTime.now().plusHours(1L));
            passwordResetTokenRepository.save(passwordResetToken);

            try {
                SimpleMailMessage passwordResetMessage = new SimpleMailMessage();
                passwordResetMessage.setTo(email);
                passwordResetMessage.setSubject("Password Reset Request");
                passwordResetMessage.setText("Password reset link will expire in 1 hour. \nTo reset your password, click here: " + buildResetPasswordUrl(token));
                mailSender.send(passwordResetMessage);
            } catch (Exception e) {
                // Rollback the database changes if email sending fails
                throw new RuntimeException("Failed to send email. Rolling back transaction.", e);
            }
            return true;
        }
        return false;
    }

    private String buildResetPasswordUrl(String token) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/password-reset")
                .queryParam("token", token)
                .toUriString();
    }

    public Optional<PasswordResetToken> getToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
}
