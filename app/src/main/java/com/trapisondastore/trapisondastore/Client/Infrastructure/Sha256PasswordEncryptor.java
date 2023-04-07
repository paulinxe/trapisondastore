package com.trapisondastore.trapisondastore.Client.Infrastructure;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.trapisondastore.trapisondastore.Client.Domain.PasswordEncryptor;

public class Sha256PasswordEncryptor implements PasswordEncryptor {

    @Override
    public String encrypt(String password) {
        MessageDigest md;

        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        md.update(password.getBytes());
        byte[] hashedPassword = md.digest();

        StringBuilder sb = new StringBuilder();

        for (byte b : hashedPassword) {
            sb.append(String.format("%02x", b));
        }

        return sb.toString();
    }

    @Override
    public boolean compare(String plain, String encrypted) {
        final String plainEncrypted = encrypt(plain);

        return plainEncrypted.equals(encrypted);
    }

}
