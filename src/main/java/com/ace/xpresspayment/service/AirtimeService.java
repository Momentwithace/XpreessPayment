package com.ace.xpresspayment.service;

import com.ace.xpresspayment.dtos.request.AirtimePurchaseDto;
import com.ace.xpresspayment.dtos.request.AirtimePurchaseRequest;
import com.ace.xpresspayment.dtos.request.PageDto;
import com.ace.xpresspayment.dtos.request.TransactionDto;
import com.ace.xpresspayment.dtos.response.AirtimePurchaseResponse;
import com.ace.xpresspayment.enums.Status;
import com.ace.xpresspayment.exceptions.XpressPaymentException;
import com.ace.xpresspayment.models.Transaction;
import com.ace.xpresspayment.repositories.TransactionRepository;
import com.ace.xpresspayment.service.client.PaymentService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AirtimeService {

    private final TransactionRepository transactionRepository;

    private final PaymentService paymentService;

    public AirtimePurchaseResponse purchaseAirtime(long userId, @Valid AirtimePurchaseRequest airtimePurchaseRequest) throws XpressPaymentException {
        Transaction transaction = new Transaction();
        transaction.setAmount(airtimePurchaseRequest.getAmount());
        transaction.setPhoneNumber(airtimePurchaseRequest.getPhoneNumber());
        transaction.setTransactionReference(generateTransactionReference());
        transaction.setNetwork(airtimePurchaseRequest.getNetwork());
        transaction.setUserId(userId);
        transaction.setStatus(Status.FAILED);
        transactionRepository.save(transaction);

        AirtimePurchaseDto.Details details = new AirtimePurchaseDto.Details();
        details.setAmount(airtimePurchaseRequest.getAmount());
        details.setPhoneNumber(airtimePurchaseRequest.getPhoneNumber());

        AirtimePurchaseDto airtimePurchaseDto = new AirtimePurchaseDto();
        airtimePurchaseDto.setDetails(details);
        airtimePurchaseDto.setRequestId(transaction.getTransactionReference());
        airtimePurchaseDto.setUniqueCode(airtimePurchaseRequest.getUniqueCode());
        AirtimePurchaseResponse airtimePurchaseResponse = paymentService.getAirtime(airtimePurchaseDto);
        transaction = transactionRepository.findByTransactionReference(airtimePurchaseResponse.getRequestId());
        if (airtimePurchaseResponse.getData() != null ){
            transaction.setStatus(Status.SUCCESSFUL);
        }
        transaction.setProcessorReference(airtimePurchaseResponse.getReferenceId());
        transactionRepository.save(transaction);
        return airtimePurchaseResponse;

    }

    private String generateTransactionReference() {
        return RandomStringUtils.randomAlphanumeric(6, 10);
    }

    public PageDto getAirtimePurchaseHistory(long userId, Pageable pageable) {
        Page<Transaction> transactionPage = transactionRepository.findAllByUserId(userId, pageable);
        List<TransactionDto> transactionDtos = transactionPage.map(transaction -> {
            TransactionDto transactionDto = new TransactionDto();
            BeanUtils.copyProperties(transaction, transactionDto);
            return transactionDto;
        }).stream().toList();
        return PageDto.build(transactionPage, transactionDtos);
    }
}
