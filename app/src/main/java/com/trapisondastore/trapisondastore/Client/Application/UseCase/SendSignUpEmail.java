package com.trapisondastore.trapisondastore.Client.Application.UseCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trapisondastore.trapisondastore.Client.Application.Command.SendSignUpEmailCommand;
import com.trapisondastore.trapisondastore.Shared.Domain.EmailSender;

@Service
public class SendSignUpEmail {
    
    @Autowired
    private EmailSender emailSender;

    public void execute(SendSignUpEmailCommand command) {
        // @TODO: would be nice to use a html template
        emailSender.send(
            command.email,
            "Welcome to Trapisondastore!",
            "We are excited to introduce our new ecommerce platform! Our ecommerce platform is designed to provide you with a seamless shopping experience that is both easy to use and secure.\n"
        );
    }
}
