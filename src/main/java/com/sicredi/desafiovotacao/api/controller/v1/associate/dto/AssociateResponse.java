package com.sicredi.desafiovotacao.api.controller.v1.associate.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.sicredi.desafiovotacao.common.DateUtils;
import com.sicredi.desafiovotacao.usecase.associate.enums.VoteStatusEnum;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AssociateResponse {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "associado_id")
    private String associateId;

    private String status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "data_votacao")
    private LocalDateTime voteDate;

    private AssociateResponse(String associateId, String voteStatus, String message, LocalDateTime voteDate) {
        this.associateId = associateId;
        this.status = voteStatus;
        this.message = message;
        this.voteDate = voteDate;
    }

    public static AssociateResponse of(String associateId, VoteStatusEnum voteStatusEnum, String message) {
        return new AssociateResponse(associateId, voteStatusEnum.getDescription(), message, DateUtils.currentDate());
    }

    public static AssociateResponse createUnableResponse() {
        return new AssociateResponse(null, VoteStatusEnum.UNABLE.getDescription(), null, null);
    }

}
