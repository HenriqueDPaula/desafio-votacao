package com.sicredi.desafiovotacao.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MessagesConstants {

    public static final String INVALID_DATE_BETWEEN = "inicio_sessao deve ser inferior a fim_sessao";
    public static final String INVALID_DATE_START = "inicio_sessao deve ser superior a data atual";
    public static final String TOPIC_NOT_FOUND = "Pauta %s não encontrada na base";
    public static final String SESSION_NOT_FOUND = "Sessão não encontrada na base";
    public static final String CLOSED_SESSION_ERROR = "Sessão {%s} encontra-se encerrada";
    public static final String UNIQUE_VOTE_CONSTRAINT = "Associado já possui voto registrado para a sessão {%s}";

    public static final String SESSION_CREATE_MESSAGE = "Sessão criada com sucesso!";


}
