package com.trapisondastore.trapisondastore.Client.Application.UseCase;

import org.springframework.beans.factory.annotation.Autowired;

import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Client.Domain.Client;
import com.trapisondastore.trapisondastore.Client.Domain.ClientRepository;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;
import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientIdException;

public class SignUpUseCase {

    @Autowired
    private ClientRepository repository;

    public void execute(SignUpCommand command) {
        Client client = null;

        try {
            client = Client.signUp(command.id, command.email, command.password);
        } catch (InvalidClientIdException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidClientEmailException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        repository.findByCriteria();
    }
}
