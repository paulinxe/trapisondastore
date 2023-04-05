package com.trapisondastore.trapisondastore.Shared.Infrastructure.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.CommandBus;

public abstract class Controller {

    @Autowired
    protected ObjectMapper mapper;

    protected Optional<ResponseEntity<ObjectNode>> failIfValidationErrors(BindingResult validation) {
        ObjectNode objectNode = mapper.createObjectNode();

        if (validation.hasErrors()) {
            for (var error : validation.getFieldErrors()) {
                objectNode.put(error.getField(), error.getDefaultMessage());
            }

            return Optional.of(new ResponseEntity<ObjectNode>(objectNode, HttpStatus.BAD_REQUEST));
        }

        return null;
    }
}
