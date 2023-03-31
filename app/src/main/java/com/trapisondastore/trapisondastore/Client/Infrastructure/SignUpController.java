package com.trapisondastore.trapisondastore.Client.Infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trapisondastore.trapisondastore.Client.Application.Command.SignUpCommand;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.Command;

import jakarta.validation.Valid;

@RestController
public class SignUpController {

    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/clients/signup")
    public ResponseEntity<ObjectNode> signup(@RequestBody @Valid SignUpRequest request, BindingResult validation) {
        ObjectNode objectNode = mapper.createObjectNode();

        if (validation.hasErrors()) {
            for (var error : validation.getFieldErrors()) {
                objectNode.put(error.getField(), error.getDefaultMessage());
            }

            return new ResponseEntity<ObjectNode>(objectNode, HttpStatus.BAD_REQUEST);
        }

        Command command = new SignUpCommand(request.name, request.email, request.password);

        objectNode.put("id", 3);
        objectNode.put("name", "test");

        return new ResponseEntity<ObjectNode>(objectNode, HttpStatus.CREATED);
    }
}