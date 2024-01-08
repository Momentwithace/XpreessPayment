package com.ace.xpresspayment.messages;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SuccessMessage {
    public static final String ACCOUNT_VERIFIED = "Account verified successfully!";
    public static final String USER_FOUND = "User found successfully!";
    public static final String SIGN_UP_SUCCESSFUL = "User created successfully!";
}
