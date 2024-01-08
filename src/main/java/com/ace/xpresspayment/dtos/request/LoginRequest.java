package com.ace.xpresspayment.dtos.request;

import com.ace.xpresspayment.myannotation.ValidEmailAddress;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @ValidEmailAddress
    private String email;

    private String password;
}
