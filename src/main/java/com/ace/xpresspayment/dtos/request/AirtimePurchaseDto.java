package com.ace.xpresspayment.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class AirtimePurchaseDto {

    private String requestId;

    private String uniqueCode;

    private Details details;

    @Setter
    @Getter
    public static class Details{
        private String phoneNumber;

        private BigDecimal amount;
    }
}
