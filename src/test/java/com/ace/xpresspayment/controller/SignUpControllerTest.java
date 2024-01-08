package com.ace.xpresspayment.controller;

import com.ace.xpresspayment.models.User;
import com.ace.xpresspayment.notification.EmailService;
import com.ace.xpresspayment.notification.OtpService;
import com.ace.xpresspayment.repositories.UserRepository;
import com.ace.xpresspayment.service.UserService;
import com.ace.xpresspayment.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.verification.VerificationMode;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.MessagingException;

import java.util.Objects;
import java.util.Properties;

import static com.ace.xpresspayment.factory.Factory.createUserDto;
import static com.ace.xpresspayment.factory.Factory.user;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;

class SignUpControllerTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
    private final EmailService emailService = mock(EmailService.class);
    private final OtpService otpService = mock(OtpService.class);


    private final UserService userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder, emailService, otpService);

    private final SignUpController signUpController = new SignUpController(userService);

    @Test
    void createUser() throws MessagingException {
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(userRepository.save(isA(User.class))).thenReturn(user());

        signUpController.createUser(createUserDto());
        verify(userRepository, times(1)).save(isA(User.class));
    }

}


