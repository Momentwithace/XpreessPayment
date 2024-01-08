package com.ace.xpresspayment.dtos.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerificationResponse {
    private String message;
    private boolean success;
}
