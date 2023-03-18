package com.sicredi.desafiovotacao.api.controller.v1.session.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SessionResponse extends RepresentationModel<SessionResponse> {

    @JsonProperty(value = "sessao_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
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

    @Getter
    @Builder
    @AllArgsConstructor
    public static class SessionResponseResult {

        @JsonProperty(value = "resultado")
        private String result;

        @JsonProperty(value = "contagem_sim")
        private int countYes;

        @JsonProperty(value = "contagem_nao")
        private int countNo;

    }
}

