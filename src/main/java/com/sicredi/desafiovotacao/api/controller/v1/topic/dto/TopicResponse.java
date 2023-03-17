package com.sicredi.desafiovotacao.api.controller.v1.topic.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TopicResponse {

    @JsonProperty(value = "pauta_id")
    private String topicId;

    @JsonProperty(value = "data_criacao")
    private LocalDateTime creationDate;

    private String message;

    public static TopicResponse of(String id, LocalDateTime creationDate, String message) {
        return new TopicResponse(id, creationDate, message);
    }
}
