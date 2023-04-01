package com.trapisondastore.trapisondastore.Client.Application.Command;

import com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command.Command;

public class SignUpCommand implements Command {
    public final String id;
    public final String email;
    public final String password;

    public SignUpCommand(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }
}
