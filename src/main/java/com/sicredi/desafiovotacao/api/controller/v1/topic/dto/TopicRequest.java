package com.sicredi.desafiovotacao.api.controller.v1.topic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class TopicRequest {

    @NotBlank(message = "assunto não deve ser vazio")
    @JsonProperty(value = "assunto")
    private String subject;

    @NotNull(message = "data_pauta não deve ser null")
    @JsonProperty("data_pauta")
    private LocalDateTime creationDate;
}
