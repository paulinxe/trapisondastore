package com.trapisondastore.trapisondastore.Client.Infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Client.Application.Command.UseCase.SignUpUseCase;
import com.trapisondastore.trapisondastore.Client.Application.Exception.UnableToSignUpException;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Controller.Controller;

import jakarta.validation.Valid;

@RestController
public class SignUpController extends Controller {

    @Autowired
    private SignUpUseCase useCase;

    @PostMapping("/clients/signup")
    public ResponseEntity<ObjectNode> signup(@RequestBody @Valid SignUpRequest request, BindingResult validation) {
        Optional<ResponseEntity<ObjectNode>> hasErrors = failIfValidationErrors(validation);

        if (hasErrors.isPresent()) {
            return hasErrors.get();
        }

        final String clientId = UUID.randomUUID().toString();
        SignUpCommand command = new SignUpCommand(clientId, request.email, request.password);

        try {
            useCase.execute(command);
        } catch (UnableToSignUpException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("id", 3);
        objectNode.put("name", "test");

        return new ResponseEntity<ObjectNode>(objectNode, HttpStatus.CREATED);
    }
}