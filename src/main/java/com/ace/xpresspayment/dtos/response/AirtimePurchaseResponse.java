package com.ace.xpresspayment.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AirtimePurchaseResponse {

    private String requestId;

    private String referenceId;

    private String responseCode;

    private String responseMessage;

    private Data data;

    @Setter
    @Getter
    public static class Data {

        private String channel;
        private BigDecimal amount;
        private String phoneNumber;
    }
}

