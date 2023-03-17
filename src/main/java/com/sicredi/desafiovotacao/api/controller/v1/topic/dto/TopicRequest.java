package com.sicredi.desafiovotacao.api.controller.v1.topic.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class TopicRequest {

    @NotBlank(message = "assunto não deve ser vazio")
    @JsonProperty(value = "assunto")
    @ApiModelProperty(notes = "Assunto da Pauta em questão", example = "Aumento salarial da diretoria", required = true)
    private String subject;

    @JsonIgnore
    private LocalDateTime creationDate;
}
