package com.sicredi.desafiovotacao.usecase.session;

import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionRequest;
import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionResponse;
import com.sicredi.desafiovotacao.entity.SessionTable;
import com.sicredi.desafiovotacao.usecase.exception.EntityNotFoundException;
import com.sicredi.desafiovotacao.usecase.exception.InvalidDateException;
import com.sicredi.desafiovotacao.usecase.session.repository.SessionRepository;
import com.sicredi.desafiovotacao.usecase.topic.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class SessionService {

    private static final String INVALID_DATE_BETWEEN = "inicio_sessao deve ser inferior a fim_sessao";
    private static final String INVALID_DATE_START = "inicio_sessao deve ser superior a data atual";
    private static final String TOPIC_NOT_FOUND = "Pauta %s não encontrada na base";

    private static final String SESSION_CREATE_MESSAGE = "Sessão criada com sucesso!";

    private final SessionRepository sessionRepository;
    private final TopicRepository topicRepository;

    @Autowired
    public SessionService(SessionRepository sessionRepository, TopicRepository topicRepository) {
        this.sessionRepository = sessionRepository;
        this.topicRepository = topicRepository;
    }

    public Optional<SessionResponse> createSession(SessionRequest sessionRequest) {
        return Optional.of(sessionRequest)
                .map(this::buildSessionEntity)
                .map(this::persistSession)
                .map(this::buildSessionResponse);
    }

    private SessionTable persistSession(SessionTable sessionTable) {
        log.info("m=persistSession l=PERSIST_SESSION sessionTable={}", sessionTable);
        return this.sessionRepository.save(sessionTable);
    }

    private SessionTable buildSessionEntity(SessionRequest sessionRequest) {

        validateSessionDate(sessionRequest);

        return SessionTable.builder()
                .topic(this.topicRepository.findById(sessionRequest.getTopicId())
                        .orElseThrow(() -> new EntityNotFoundException(String.format(TOPIC_NOT_FOUND, sessionRequest.getTopicId()))))
                .creationDate(LocalDateTime.now())
                .startDate(sessionRequest.getStartDate())
                .endDate(sessionRequest.getEndDate())
                .build();
    }

    private SessionResponse buildSessionResponse(SessionTable sessionTable) {
        return SessionResponse.of(sessionTable.getId(), SESSION_CREATE_MESSAGE, sessionTable.getStartDate(),
                sessionTable.getEndDate());
    }

    /**
     * Caso não seja informado a data de inicio, a sessão é aberta em 5 minutos.
     * Caso não seja informado a data de fim, a sessão dura 1 minuto.
     * @param sessionRequest
     *
     * @throws InvalidDateException caso a data_inicio seja inferior a data atual
     * @throws InvalidDateException caso a data_inicio seja superior a data_fim
     */
    private void validateSessionDate(SessionRequest sessionRequest) {
        LocalDateTime startDate = sessionRequest.getStartDate();
        LocalDateTime endDate = sessionRequest.getEndDate();

        if (Objects.isNull(startDate)) {
            startDate = LocalDateTime.now().plus(5, ChronoUnit.MINUTES);
        }

        if (Objects.isNull(endDate)) {
            endDate = startDate.plus(1, ChronoUnit.MINUTES);
        }

        if (endDate.isBefore(startDate)) {
            throw new InvalidDateException(INVALID_DATE_BETWEEN, startDate, endDate);
        }

        if (startDate.isBefore(LocalDateTime.now())) {
            throw new InvalidDateException(INVALID_DATE_START, startDate, endDate);
        }

        sessionRequest.setStartDate(startDate);
        sessionRequest.setEndDate(endDate);
    }
}
