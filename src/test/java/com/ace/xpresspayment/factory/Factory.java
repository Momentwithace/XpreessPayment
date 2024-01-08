package com.ace.xpresspayment.factory;

import com.ace.xpresspayment.dtos.request.AirtimePurchaseRequest;
import com.ace.xpresspayment.dtos.request.CreateUserRequest;
import com.ace.xpresspayment.dtos.response.AirtimePurchaseResponse;
import com.ace.xpresspayment.enums.Network;
import com.ace.xpresspayment.models.Transaction;
import com.ace.xpresspayment.models.User;

import javax.validation.Valid;
import java.math.BigDecimal;

public class Factory {
    public static AirtimePurchaseRequest airtimeRequest() {
        AirtimePurchaseRequest airtimePurchaseRequest = new AirtimePurchaseRequest();
        airtimePurchaseRequest.setAmount(new BigDecimal(100));
        airtimePurchaseRequest.setNetwork(Network.MTN);
        airtimePurchaseRequest.setPhoneNumber("0810101010101");
        airtimePurchaseRequest.setUniqueCode("MTN_22432");
        return airtimePurchaseRequest;
    }

    public static AirtimePurchaseResponse airtimePurchaseResponse() {
        AirtimePurchaseResponse airtimePurchaseResponse = new AirtimePurchaseResponse();
        airtimePurchaseResponse.setRequestId("qwerty");
        airtimePurchaseResponse.setReferenceId("12345");

        return airtimePurchaseResponse;
    }

    public static Transaction transaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(100));
        transaction.setNetwork(Network.MTN);
        transaction.setUserId(1L);
        transaction.setPhoneNumber("0810101010101");
        return transaction;
    }

    public static User user() {
        User user = new User();
        user.setEmail("test@yahoo.com");
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        return user;
    }

    public static @Valid CreateUserRequest createUserDto() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setEmail("test@yahoo.com");
        createUserRequest.setFirstName("John");
        createUserRequest.setLastName("Doe");
        createUserRequest.setPassword("Qwjei2i3jwqh1@");
        return createUserRequest;
    }

    private boolean verify(String token, String email) {
        return false;
    }

}
