package com.sicredi.desafiovotacao.usecase.exception;

public class EntityNotFoundException extends RuntimeException{

    public EntityNotFoundException(String message) {
        super(message);
    }

}
