package com.trapisondastore.trapisondastore.Client.Infrastructure;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.Command;
import com.trapisondastore.trapisondastore.Shared.Infrastructure.Controller.Controller;

import jakarta.validation.Valid;

@RestController
public class SignUpController extends Controller {

    @PostMapping("/clients/signup")
    public ResponseEntity<ObjectNode> signup(@RequestBody @Valid SignUpRequest request, BindingResult validation) {
        Optional<ResponseEntity<ObjectNode>> hasErrors = failIfValidationErrors(validation);

        if (hasErrors.isPresent()) {
            return hasErrors.get();
        }

        Command command = new SignUpCommand(request.name, request.email, request.password);

        commandBus.dispatch(command);

        ObjectNode objectNode = mapper.createObjectNode();
        objectNode.put("id", 3);
        objectNode.put("name", "test");

        return new ResponseEntity<ObjectNode>(objectNode, HttpStatus.CREATED);
    }
}