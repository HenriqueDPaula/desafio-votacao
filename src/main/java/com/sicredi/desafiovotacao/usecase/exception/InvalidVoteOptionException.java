package com.sicredi.desafiovotacao.usecase.exception;

public class InvalidVoteOptionException extends RuntimeException {

    public InvalidVoteOptionException(String message) {
        super(message);
    }

}
