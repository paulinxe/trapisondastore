package com.trapisondastore.trapisondastore.Client.Application.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trapisondastore.trapisondastore.Shared.Domain.EmailSender;

@Service
public class SendSignUpEmail {
    
    @Autowired
    private EmailSender emailSender;

    public void execute() {
        emailSender.send("test@test.com", "test", "test");
    }
}
