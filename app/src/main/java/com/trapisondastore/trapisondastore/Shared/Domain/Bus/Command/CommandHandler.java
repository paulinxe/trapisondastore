package com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command;

public interface CommandHandler {
    public void handle(Command command);
}
