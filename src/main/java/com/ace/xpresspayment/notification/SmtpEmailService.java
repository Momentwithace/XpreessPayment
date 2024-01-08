package com.ace.xpresspayment.notification;

import com.ace.xpresspayment.dtos.request.SendEmailRequest;
import com.ace.xpresspayment.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.util.StringUtils.capitalize;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmtpEmailService implements EmailService {
    private final JavaMailSender javaMailSender;

    @Value("augustineezekiel763@gmail.com")
    private String sender;

    @Value("${origin}")
    private String origin;

    private final ResourceLoader resourceLoader;

    @Override
    public void signUpMessage(User user, String token) throws MessagingException {
        sendEmail(SendEmailRequest.builder()
                .email(user.getEmail())
                .subject("Verification for your XpressPayment account")
                .message(String.format(readHtmlFile("welcome.html"),
                        capitalize(user.getFirstName().toLowerCase()),
                        token))
                .build());
    }


    private void sendEmail(SendEmailRequest sendEmailRequest) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        message.setFrom(new InternetAddress("xpresspayment@gmail.com"));
        message.setRecipients(MimeMessage.RecipientType.TO, sendEmailRequest.getEmail());
        message.setSubject(sendEmailRequest.getSubject());
        message.setContent(sendEmailRequest.getMessage(), "text/html; charset=utf-8");

        javaMailSender.send(message);
    }

    public String readHtmlFile(String file) {
        try {
            InputStream inputStream = resourceLoader.getResource("classpath:" + file).getInputStream();
            return new String(FileCopyUtils.copyToByteArray(inputStream), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file");
        }
    }

}
