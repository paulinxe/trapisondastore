package com.trapisondastore.trapisondastore.Client.Infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.Command;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.CommandBus;

@RestController
public class ClientController {
    
    @Autowired
    private CommandBus commandBus;

    @GetMapping("/test")
    public void test() {
        Command command = new SignUpCommand();
        
        commandBus.dispatch(command);
    }
}
