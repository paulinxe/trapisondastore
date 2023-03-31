package com.trapisondastore.trapisondastore.Shared.Domain.Bus.Command;

public interface CommandBus {
    public void dispatch(Command command);
}
