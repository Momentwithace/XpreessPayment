package com.ace.xpresspayment.controller;

import com.ace.xpresspayment.dtos.request.LoginRequest;
import com.ace.xpresspayment.dtos.response.BaseResponse;
import com.ace.xpresspayment.messages.SuccessMessage;
import com.ace.xpresspayment.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/xpress")
public class LoginController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authorize")
    public BaseResponse<?> authorize(@RequestBody @Valid LoginRequest loginRequest) {
        String password = loginRequest.getPassword();
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), password));
        String response  = tokenProvider.generateJWTToken(authenticate);
        return BaseResponse.<String>builder()
                .data(response)
                .isSuccessful(true)
                .statusCode(HttpStatus.OK.value())
                .message(SuccessMessage.SIGN_UP_SUCCESSFUL)
                .timestamp(java.time.LocalDateTime.now())
                .build();

    }
}
