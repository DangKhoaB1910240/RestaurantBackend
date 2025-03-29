package com.example.event.resetpassword;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.event.config.JwtGenerator;
import com.example.event.email.EmailService;
import com.example.event.logger.LoggerService;
import com.example.event.role.RoleRepository;
import com.example.event.user.User;
import com.example.event.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    @Autowired // tiêm phụ thuộc vào
    public PasswordResetService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            PasswordResetTokenRepository tokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public void sendResetPasswordEmail(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Email không tồn tại"));

        // Xóa token cũ nếu có
        tokenRepository.findByUser(user).ifPresent(tokenRepository::delete);

        // Tạo token mới
        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryDate(LocalDateTime.now().plusMinutes(30)); // Token hết hạn sau 30 phút

        tokenRepository.save(resetToken);

        // Gửi email chứa link đặt lại mật khẩu
        String resetLink = "http://localhost:4200/reset-password?token=" + token;
        this.emailService.sendEmail2(user.getEmail(), "Đặt lại mật khẩu",
                "Nhấp vào link sau để đặt lại mật khẩu: " + resetLink);
    }

    public void resetPassword(ResetPasswordRequest request) {
        String token = request.getToken();
        String newPassword = request.getNewPassword();
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Token không hợp lệ"));

        if (resetToken.isExpired()) {
            throw new IllegalArgumentException("Token đã hết hạn");
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword)); // Bạn nên mã hóa mật khẩu trước khi lưu
        userRepository.save(user);

        // Xóa token sau khi sử dụng
        tokenRepository.delete(resetToken);
    }
}