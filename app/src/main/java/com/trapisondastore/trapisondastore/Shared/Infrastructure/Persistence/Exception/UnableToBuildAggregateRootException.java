package com.trapisondastore.trapisondastore.Shared.Infrastructure.Persistence.Exception;

public class UnableToBuildAggregateRootException extends Exception {

    public UnableToBuildAggregateRootException(Throwable e) {
        super(e);
    }
}
