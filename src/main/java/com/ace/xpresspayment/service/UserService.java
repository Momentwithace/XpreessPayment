package com.ace.xpresspayment.service;


import com.ace.xpresspayment.dtos.request.CreateUserRequest;
import com.ace.xpresspayment.dtos.response.SignUpResponse;

import javax.mail.MessagingException;

public interface UserService {

    SignUpResponse createUser(CreateUserRequest signupDto) throws MessagingException;
    Object verify(String token, String email);

}
