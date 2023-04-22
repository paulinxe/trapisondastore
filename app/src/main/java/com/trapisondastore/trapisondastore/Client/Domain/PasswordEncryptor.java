package com.trapisondastore.trapisondastore.Client.Domain;

import org.springframework.stereotype.Service;

@Service
public interface PasswordEncryptor {
    public String encrypt(String password);
    public boolean compare(String plain, String encrypted);
}
