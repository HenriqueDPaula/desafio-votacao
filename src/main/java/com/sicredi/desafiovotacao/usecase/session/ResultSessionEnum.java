package com.sicredi.desafiovotacao.usecase.session;

import lombok.Getter;

public enum ResultSessionEnum {

    DRAW("Empate"),
    APPROVED("Aprovado"),
    DENIED("Negado");

    @Getter
    private String description;

    ResultSessionEnum(String description) {
        this.description = description;
    }

}
