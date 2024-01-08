package com.ace.xpresspayment.controller;


import com.ace.xpresspayment.dtos.request.AirtimePurchaseDto;
import com.ace.xpresspayment.dtos.request.PageDto;
import com.ace.xpresspayment.dtos.request.ResponseDto;
import com.ace.xpresspayment.dtos.request.TransactionDto;
import com.ace.xpresspayment.dtos.response.AirtimePurchaseResponse;
import com.ace.xpresspayment.exceptions.XpressPaymentException;
import com.ace.xpresspayment.models.CustomUser;
import com.ace.xpresspayment.models.Transaction;
import com.ace.xpresspayment.repositories.TransactionRepository;
import com.ace.xpresspayment.service.AirtimeService;
import com.ace.xpresspayment.service.client.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.math.BigDecimal;
import java.util.Collections;

import static com.ace.xpresspayment.factory.Factory.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AirtimeControllerTest {
    private Authentication authentication;
    private final TransactionRepository transactionRepository = mock(TransactionRepository.class);

    private final PaymentService paymentService = mock(PaymentService.class);
    private final AirtimeService airtimeService = new AirtimeService(transactionRepository, paymentService);
    private final AirtimeController airtimeController = new AirtimeController(airtimeService);

    @BeforeEach
    void setUp() {
        authentication = mock(Authentication.class);
    }

    @Test
    void testToBuyAirtime() throws XpressPaymentException {
        when(authentication.getPrincipal()).thenReturn(iUserDetails());

        when(transactionRepository.save(isA(Transaction.class))).thenReturn(transaction());
        when(paymentService.getAirtime(isA(AirtimePurchaseDto.class))).thenReturn(airtimePurchaseResponse());
        when(transactionRepository.findByTransactionReference(anyString())).thenReturn(transaction());

        ResponseDto<?> responseDto = airtimeController.purchaseAirtime(authentication, airtimeRequest());
        AirtimePurchaseResponse airtimePurchaseResponse1 = (AirtimePurchaseResponse) responseDto.getData();

        assertEquals("12345", airtimePurchaseResponse1.getReferenceId());
        verify(authentication, times(1)).getPrincipal();
    }

    @Test
    void testToGetAirtimeHistory() {
        when(authentication.getPrincipal()).thenReturn(iUserDetails());
        Page<Transaction> transactions = new PageImpl<>(Collections.nCopies(2, transaction()));
        when(transactionRepository.findAllByUserId(eq(1L), any(Pageable.class))).thenReturn(transactions);

        PageDto result = airtimeController.getAirtimeHistory(authentication, 1, 3);
        TransactionDto transactionDto = (TransactionDto) result.getData().get(0);
        assertEquals(2, result.getTotalItems());
        assertEquals(new BigDecimal(100), transactionDto.getAmount());
        verify(authentication, times(1)).getPrincipal();
    }

    private CustomUser iUserDetails() {
        CustomUser customUser = new CustomUser();
        customUser.setId(1);
        customUser.setEnabled(true);
        return customUser;
    }
}