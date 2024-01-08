package com.ace.xpresspayment.service;

import com.ace.xpresspayment.messages.ErrorMessage;
import com.ace.xpresspayment.messages.SuccessMessage;
import com.ace.xpresspayment.models.CustomUser;
import com.ace.xpresspayment.models.User;
import com.ace.xpresspayment.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class IUserService implements UserDetailsService {


    private final UserRepository userRepository;

    @Override
    public CustomUser loadUserByUsername(String email) {
        User user = userRepository.findUserByEmail(email);
        log.info(SuccessMessage.USER_FOUND, user);
        if (user == null) {
            throw new UsernameNotFoundException(ErrorMessage.USER_NOT_FOUND + " " + email);
        }
        return new CustomUser(user.getId(), user.getEmail(),user.getPassword(), user.getRole(), user.isEmailVerified());
    }
}
