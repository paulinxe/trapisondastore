package com.trapisondastore.trapisondastore.Client.Infrastructure;

import jakarta.validation.constraints.NotEmpty;

public class SignUpRequest {
    @NotEmpty
    public String name;

    @NotEmpty
    public String email;

    @NotEmpty
    public String password;
}

