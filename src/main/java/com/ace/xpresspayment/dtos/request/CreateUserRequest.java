package com.ace.xpresspayment.dtos.request;

import com.ace.xpresspayment.myannotation.DoesNotExist;
import com.ace.xpresspayment.myannotation.ValidEmailAddress;
import com.ace.xpresspayment.myannotation.ValidPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserRequest {
    @Size(max = 20, min = 2, message = "{firstname.accepted_length}")
    @NotBlank(message = "{firstname.not_blank}")
    private String firstName;

    @Size(max = 20, min = 2, message = "{lastname.accepted_length}")
    @NotBlank(message = "{lastname.not_blank}")
    private String lastName;

    @ValidEmailAddress
    @NotBlank(message = "{email.not_blank}")
    @DoesNotExist(table = "users", columnName = "email", message = "{user.email_exists}")
    private String email;

    @NotBlank(message = "{password.not.empty}")
    @ValidPassword(message = "{password.not.strong}")
    private String password;
}