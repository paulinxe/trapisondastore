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
import com.trapisondastore.trapisondastore.Client.Application.Exception.UnableToSignUpException;
import com.trapisondastore.trapisondastore.Client.Application.UseCase.SignUpUseCase;
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
        ObjectNode objectNode = mapper.createObjectNode();

        try {
            useCase.execute(command);
        } catch (UnableToSignUpException e) {
            String fieldName = "";
            String errorMessage = "";
            var status = HttpStatus.BAD_REQUEST;

            switch (e.ERROR_CODE) {
                case UnableToSignUpException.CLIENT_EXISTS:
                    fieldName = "email";
                    errorMessage = "Client already exists";
                    break;
                case UnableToSignUpException.EMAIL_NOT_VALID:
                    fieldName = "email";
                    errorMessage = "Email not valid";
                    break;
                case UnableToSignUpException.PASSWORD_NOT_VALID:
                    fieldName = "password";
                    errorMessage = "Password must have 8 characters minimum, at least one number and at least one letter";
                    break;
                case UnableToSignUpException.EVENT_PUBLISHER_FAILED:
                    fieldName = "error";
                    errorMessage = "Internal server error";
                    status = HttpStatus.INTERNAL_SERVER_ERROR;
                    break;
            }

            objectNode.put(fieldName, errorMessage);

            return new ResponseEntity<ObjectNode>(objectNode, status);
        }

        objectNode.put("id", clientId);

        return new ResponseEntity<ObjectNode>(objectNode, HttpStatus.CREATED);
    }
}