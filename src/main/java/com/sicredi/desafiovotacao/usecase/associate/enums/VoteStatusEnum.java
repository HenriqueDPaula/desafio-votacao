package com.sicredi.desafiovotacao.usecase.associate.enums;

import lombok.Getter;

public enum VoteStatusEnum {

    ABLE("ABLE_TO_VOTE"),
    UNABLE("UNABLE_TO_VOTE");

    @Getter
    private String description;

    VoteStatusEnum(String description) {
        this.description = description;
    }


}
