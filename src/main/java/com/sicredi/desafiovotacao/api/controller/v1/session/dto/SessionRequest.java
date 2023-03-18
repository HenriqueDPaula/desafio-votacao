package com.sicredi.desafiovotacao.api.controller.v1.session.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class SessionRequest {

    @NotBlank(message = "pauta_id não deve ser vazio")
    @JsonProperty(value = "pauta_id")
    @ApiModelProperty(notes = "Id da pauta", example = "968c3966c51011edafa10242ac120002", required = true)
    private String topicId;

    @JsonProperty(value = "inicio_sessao")
    @ApiModelProperty(notes = "Data de abertuda da sessão, valor padrão caso não informado será de 1 minuto " +
            "a partir da requisição", example = "2023-17-20T20:30:55")
    private LocalDateTime startDate;

    @JsonProperty(value = "fim_sessao")
    @ApiModelProperty(notes = "Data de encerramento da sessão, valor padrão caso não informado será de 1 minuto " +
            "a partir da data de abertuda", example = "2023-17-20T20:35:55")
    private LocalDateTime endDate;
}
