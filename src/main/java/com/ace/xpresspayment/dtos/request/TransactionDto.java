package com.ace.xpresspayment.dtos.request;

import com.ace.xpresspayment.enums.Network;
import com.ace.xpresspayment.models.Transaction;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class TransactionDto {

    private BigDecimal amount;

    private String phoneNumber;

    private Network network;

    private String processorReference;

    private String transactionReference;

    private Date createdAt;

    public static TransactionDto fromModel(Transaction transaction) {
        TransactionDto transactionDto = new TransactionDto();
        BeanUtils.copyProperties(transaction, transactionDto);
        return transactionDto;
    }

    public static TransactionDto toDto(Object o) {
        return fromModel((Transaction) o);
    }
}
