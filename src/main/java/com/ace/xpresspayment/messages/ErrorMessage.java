package com.ace.xpresspayment.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {
    public static final String USER_NOT_FOUND = "User not found!";
    public static final String INVALID_OTP = "Invalid OTP!";
    public static final String ERROR_DURING_ACCOUNT_VERIFICATION = "Error during account verification!";
    public static final String INVALID_PHONE_NUMBER = "Invalid phone number!";
    public static final String AIRTIME_PURCHASE_UNSUCCESSFUL = "Airtime purchase unsuccessful!";
}
