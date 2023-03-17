package com.sicredi.desafiovotacao.api.controller;

import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionResponse;
import com.sicredi.desafiovotacao.usecase.exception.*;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> processValidationException(final MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .sorted()
                .collect(Collectors.joining(", ", "", "."));

        return buildExceptionResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ExceptionHandler(InvalidDateException.class)
    @ResponseBody
    public ResponseEntity<SessionResponse> processInvalidDateException(final InvalidDateException ex) {
        return buildSessionResponse(HttpStatus.BAD_REQUEST, ex);
    }

    @ExceptionHandler(InvalidVoteOptionException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> processInvalidVoteOption(final InvalidVoteOptionException ex) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<ExceptionResponse> processEntityNotFoundException(final EntityNotFoundException ex) {
        return buildExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler({ClosedSessionException.class, UniqueVoteViolationException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> processEntityNotFoundException(final Exception ex) {
        return buildExceptionResponse(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
    }

    private ResponseEntity<ExceptionResponse> buildExceptionResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(new ExceptionResponse(status.getReasonPhrase(), message), status);
    }

    private ResponseEntity<SessionResponse> buildSessionResponse(HttpStatus status, InvalidDateException ex) {
        return new ResponseEntity<>(SessionResponse.of("", ex.getMessage(), ex.getStartDate(), ex.getEndDate()), status);
    }
}