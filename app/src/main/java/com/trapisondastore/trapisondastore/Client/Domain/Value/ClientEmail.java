package com.trapisondastore.trapisondastore.Client.Domain.Value;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trapisondastore.trapisondastore.Client.Domain.Exception.InvalidClientEmailException;

public final class ClientEmail {
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private String value;

    public ClientEmail() {
        
    }

    public ClientEmail(String email) throws InvalidClientEmailException {
        if (email == null) {
            throw new InvalidClientEmailException();
        }

        Matcher matcher = EMAIL_PATTERN.matcher(email);
        
        if (!matcher.matches()) {
            throw new InvalidClientEmailException();
        }

        this.value = email;
    }

    public String value() {
        return value;
    }
}
