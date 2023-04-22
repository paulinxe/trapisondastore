package com.trapisondastore.trapisondastore.Shared.Infrastructure.Email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.trapisondastore.trapisondastore.Shared.Domain.EmailSender;

@Service
public class EmailSenderJavaMail implements EmailSender {
    
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("noreply@trapisondastore.com");

        mailSender.send(message);
    }
    
}
