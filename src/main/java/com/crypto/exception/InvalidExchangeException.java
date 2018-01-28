package com.crypto.exception;

public class InvalidExchangeException extends Exception {

    public InvalidExchangeException() { super(); }
    public InvalidExchangeException(String message) { super(message); }
    public InvalidExchangeException(String message, Throwable cause) { super(message, cause); }
    public InvalidExchangeException(Throwable cause) { super(cause); }

}
