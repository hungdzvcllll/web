package com.web.web.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.web.web.service.EmailSenderInterface;

@Service
public class EmailSender implements EmailSenderInterface {
    @Autowired
    JavaMailSender sender;

    public void sendEmail(String token, String email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("namdinh1020@gmail.com");
        message.setTo(email);
        message.setSubject("Xac nhan dang ky");
        message.setText(token);
        sender.send(message);
    }

}
