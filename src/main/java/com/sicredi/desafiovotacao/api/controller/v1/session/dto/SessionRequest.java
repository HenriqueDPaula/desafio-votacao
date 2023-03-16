package com.sicredi.desafiovotacao.api.controller.v1.session.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
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
    private String topicId;

    @JsonProperty(value = "inicio_sessao")
    private LocalDateTime startDate;

    @JsonProperty(value = "fim_sessao")
    private LocalDateTime endDate;
}
