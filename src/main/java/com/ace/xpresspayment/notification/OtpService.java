package com.ace.xpresspayment.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepo otpRepo;

    public String generateOtp(String email) {
        OTP otp = OTP.builder()
                .email(email)
                .code(gen6())
                .build();
        otpRepo.save(otp);
        return otp.getCode();
    }

    private String gen6() {
        SecureRandom secureRandom = new SecureRandom();
        int min = 100000;
        int max = 999999;
        return String.valueOf(secureRandom.nextInt(max - min + 1) + min);
    }

    public boolean validateOtp(String token, String email) {
        OTP otp = otpRepo.findByCodeAndEmail(token, email).orElseThrow(
                () -> new RuntimeException("Invalid OTP")
        );
        otpRepo.delete(otp);
        return true;
    }
}
