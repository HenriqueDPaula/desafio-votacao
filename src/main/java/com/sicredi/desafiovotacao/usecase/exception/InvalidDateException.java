package com.sicredi.desafiovotacao.usecase.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class InvalidDateException extends RuntimeException {

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public InvalidDateException(String errorMessage, LocalDateTime startDate, LocalDateTime endDate) {
        super(errorMessage);
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
