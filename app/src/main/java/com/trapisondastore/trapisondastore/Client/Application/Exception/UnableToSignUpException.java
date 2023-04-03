package com.trapisondastore.trapisondastore.Client.Application.Exception;

public class UnableToSignUpException extends Exception {
    public final int ERROR_CODE;
    public static final int CLIENT_EXISTS = 1;
    public static final int EMAIL_NOT_VALID = 2;
    public static final int PASSWORD_NOT_VALID = 3;

    public UnableToSignUpException(int errorCode) {
        this.ERROR_CODE = errorCode;
    }

    public static UnableToSignUpException clientAlreadyExists() {
        return new UnableToSignUpException(CLIENT_EXISTS);
    }

    public static UnableToSignUpException emailNotValid() {
        return new UnableToSignUpException(EMAIL_NOT_VALID);
    }

    public static UnableToSignUpException passwordNotValid() {
        return new UnableToSignUpException(PASSWORD_NOT_VALID);
    }
}
