package com.ace.xpresspayment.notification;


import com.ace.xpresspayment.models.User;

import javax.mail.MessagingException;

public interface EmailService {

    void signUpMessage(User user, String token) throws MessagingException;


}
