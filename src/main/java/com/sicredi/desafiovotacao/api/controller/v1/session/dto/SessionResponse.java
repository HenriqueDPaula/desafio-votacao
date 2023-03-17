package com.sicredi.desafiovotacao.api.controller.v1.session.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponse {

    @JsonProperty(value = "sessao_id")
    private String sessionId;

    private String message;

    @JsonProperty(value = "data_inicio_sessao")
    private LocalDateTime startDate;

    @JsonProperty(value = "data_fim_sessao")
    private LocalDateTime endDate;

    public static SessionResponse of(String id, String message, LocalDateTime startDate, LocalDateTime endDate) {
        return new SessionResponse(id, message, startDate, endDate);
    }

    public static SessionResponse createEmptyResponse() {
        return new SessionResponse();
    }

}
