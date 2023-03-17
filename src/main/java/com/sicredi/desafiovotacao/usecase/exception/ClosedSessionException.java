package com.sicredi.desafiovotacao.usecase.exception;

public class ClosedSessionException extends RuntimeException {

    public ClosedSessionException(String message) {
        super(message);
    }
}
