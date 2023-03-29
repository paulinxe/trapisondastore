package com.trapisondastore.trapisondastore.Client.Domain.Value;

public final class ClientPassword {
    private String password;

    public ClientPassword(String password) {
        this.password = password;
    }

    public String value() {
        return password;
    }
}
