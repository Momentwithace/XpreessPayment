package com.ace.xpresspayment.dtos.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendEmailRequest {
    private String email;
    private String message;
    private String subject;
}
