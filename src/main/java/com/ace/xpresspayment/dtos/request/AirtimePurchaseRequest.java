package com.ace.xpresspayment.dtos.request;

import com.ace.xpresspayment.enums.Network;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Setter
@Getter
public class AirtimePurchaseRequest {
    @NotBlank(message = "{phone.not_blank}")
    private String phoneNumber;

    @NotBlank(message = "{unique_code.not_blank}")
    private String uniqueCode;

    @Positive(message = "{positive.value}")
    private BigDecimal amount;

    @NotNull
    private Network network;
}
