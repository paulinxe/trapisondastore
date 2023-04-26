package com.trapisondastore.trapisondastore.Shared.Domain;

public interface EmailSender {
    public void send(String to, String subject, String body);
}
