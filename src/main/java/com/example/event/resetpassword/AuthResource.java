package com.example.event.resetpassword;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = { "http://localhost:4200" })
public class AuthResource {

    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/forgot-password")
    public ResponseEntity<Map<String, String>> forgotPassword(
            @RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        System.out.println(forgotPasswordRequest.getEmail());
        this.passwordResetService.sendResetPasswordEmail(forgotPasswordRequest);
        return ResponseEntity.ok(Collections.singletonMap("message", "Đã gửi email đặt lại mật khẩu"));

    }

    @PostMapping("/reset-password")
    public ResponseEntity<Map<String, String>> resetPassword(@RequestBody ResetPasswordRequest request) {
        this.passwordResetService.resetPassword(request);
        System.out.println(111);
        return ResponseEntity.ok(Collections.singletonMap("message", "Mật khẫu đã được đặt lại"));
    }
}