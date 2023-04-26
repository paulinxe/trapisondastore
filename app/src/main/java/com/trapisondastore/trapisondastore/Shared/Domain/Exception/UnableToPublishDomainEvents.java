package com.trapisondastore.trapisondastore.Shared.Domain.Exception;

public class UnableToPublishDomainEvents extends Exception {
    public UnableToPublishDomainEvents() {

    }
    
    public UnableToPublishDomainEvents(String message, Throwable e) {
        super(message, e);
    }
}
