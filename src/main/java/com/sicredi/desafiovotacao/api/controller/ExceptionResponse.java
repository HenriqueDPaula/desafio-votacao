package com.sicredi.desafiovotacao.api.controller;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {

    private String httpStatus;
    private String message;
}