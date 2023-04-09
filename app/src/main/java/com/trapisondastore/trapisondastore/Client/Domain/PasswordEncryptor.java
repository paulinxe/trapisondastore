package com.trapisondastore.trapisondastore.Client.Domain;

public interface PasswordEncryptor {
    public String encrypt(String password);
    public boolean compare(String plain, String encrypted);
}
