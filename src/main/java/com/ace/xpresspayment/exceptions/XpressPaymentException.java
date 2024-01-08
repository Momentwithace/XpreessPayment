package com.ace.xpresspayment.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class XpressPaymentException extends Exception {
    private final HttpStatus status;

    public XpressPaymentException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public static XpressPaymentException badRequest(String message) {
        return new XpressPaymentException(message, HttpStatus.BAD_REQUEST);
    }
}
