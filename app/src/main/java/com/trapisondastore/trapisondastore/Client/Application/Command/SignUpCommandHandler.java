package com.trapisondastore.trapisondastore.Client.Application.Command;

import org.springframework.beans.factory.annotation.Autowired;

import com.trapisondastore.trapisondastore.Client.Application.Command.UseCase.SignUpUseCase;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.Command;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.CommandHandler;

public class SignUpCommandHandler implements CommandHandler {

    @Autowired
    private SignUpUseCase useCase;

    @Override
    public void handle(Command command) {
        useCase.execute((SignUpCommand) command);
    }
    
}
