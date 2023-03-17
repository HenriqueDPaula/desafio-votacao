package com.sicredi.desafiovotacao.usecase.exception;

public class UniqueVoteViolationException extends RuntimeException {

    public UniqueVoteViolationException(String message) {
        super(message);
    }
}
