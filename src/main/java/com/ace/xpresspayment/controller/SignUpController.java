package com.ace.xpresspayment.controller;

import com.ace.xpresspayment.dtos.request.CreateUserRequest;
import com.ace.xpresspayment.dtos.response.BaseResponse;
import com.ace.xpresspayment.dtos.response.SignUpResponse;
import com.ace.xpresspayment.messages.SuccessMessage;
import com.ace.xpresspayment.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/xpress")
public class SignUpController {

    private final UserService userServiceImpl;

    @PostMapping("/signUp")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse<SignUpResponse> createUser(@RequestBody @Valid CreateUserRequest signupDto) throws MessagingException {
        SignUpResponse response = userServiceImpl.createUser(signupDto);
        return BaseResponse.<SignUpResponse>builder()
                .data(response)
                .isSuccessful(true)
                .statusCode(HttpStatus.CREATED.value())
                .message(SuccessMessage.SIGN_UP_SUCCESSFUL + response.getEmail())
                .timestamp(LocalDateTime.now())
                .build();
   }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestParam String token, @RequestParam String email) {
        return ResponseEntity.ok(userServiceImpl.verify(token, email));
    }


}
