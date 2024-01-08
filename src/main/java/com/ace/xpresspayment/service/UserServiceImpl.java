package com.ace.xpresspayment.service;


import com.ace.xpresspayment.dtos.request.CreateUserRequest;
import com.ace.xpresspayment.dtos.response.SignUpResponse;
import com.ace.xpresspayment.dtos.response.VerificationResponse;
import com.ace.xpresspayment.enums.Role;
import com.ace.xpresspayment.messages.ErrorMessage;
import com.ace.xpresspayment.messages.SuccessMessage;
import com.ace.xpresspayment.models.User;
import com.ace.xpresspayment.notification.EmailService;
import com.ace.xpresspayment.notification.OtpService;
import com.ace.xpresspayment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final OtpService otpService;

    public SignUpResponse createUser(CreateUserRequest createUserRequest) throws MessagingException {
        User user = new User();
        BeanUtils.copyProperties(createUserRequest, user);
        final String hashedPassword = passwordEncoder.encode(createUserRequest.getPassword());
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);
        userRepository.save(user);
        String opt = otpService.generateOtp(user.getEmail());
        emailService.signUpMessage(user, opt);
        return SignUpResponse.builder()
                .email(user.getEmail())
                .build();
    }

    @Override
    public Object verify(String token, String email) {
        try {
            if (otpService.validateOtp(token, email)) {
                User user = userRepository.findByEmail(email);
                user.setEmailVerified(true);
                userRepository.save(user);
                return VerificationResponse.builder()
                        .message(SuccessMessage.ACCOUNT_VERIFIED)
                        .success(true)
                        .build();
            } else {
                throw new RuntimeException(ErrorMessage.INVALID_OTP);
            }
        } catch (Exception e) {
            log.info(ErrorMessage.ERROR_DURING_ACCOUNT_VERIFICATION, e);
            return VerificationResponse.builder()
                    .message(ErrorMessage.ERROR_DURING_ACCOUNT_VERIFICATION)
                    .success(false)
                    .build();
        }    }
}
