package com.sicredi.desafiovotacao.usecase.session;

import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionRequest;
import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionResponse;
import com.sicredi.desafiovotacao.common.DateUtils;
import com.sicredi.desafiovotacao.entity.SessionTable;
import com.sicredi.desafiovotacao.entity.TopicTable;
import com.sicredi.desafiovotacao.usecase.session.repository.SessionRepository;
import com.sicredi.desafiovotacao.usecase.topic.repository.TopicRepository;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mockStatic;

public class SessionServiceTestSupport {

    @SpyBean
    protected SessionResponse sessionResponse;

    @Autowired
    protected SessionService sessionService;

    @Autowired
    protected SessionRepository sessionRepository;

    @Autowired
    protected TopicRepository topicRepository;

    protected TopicTable topicTable;

    protected boolean isSessionOpen;

    protected void runCreateSession(LocalDateTime currentDate, LocalDateTime startDate, LocalDateTime endDate) {
        // Necessário mockar a hora do sistema
        try (MockedStatic<DateUtils> dateMock = mockStatic(DateUtils.class)) {
            dateMock.when(DateUtils::currentDate)
                    .thenReturn(currentDate);

            var sessionRequest = buildSessionRequest(startDate, endDate);

            this.sessionService.createSession(sessionRequest)
                    .ifPresent(response -> this.sessionResponse = response);
        }
    }

    protected void runIsSessionOpen(SessionTable sessionTable, LocalDateTime currentDate) {
        try (MockedStatic<DateUtils> dateMock = mockStatic(DateUtils.class)) {
            dateMock.when(DateUtils::currentDate)
                    .thenReturn(currentDate);
            isSessionOpen = this.sessionService.isSessionOpen(sessionTable);
        }
    }

    protected SessionTable runFindById(String id) {
        return this.sessionService.findById(id);
    }

    protected SessionRequest buildSessionRequest(LocalDateTime startDate, LocalDateTime endDate) {
        return new SessionRequest(this.topicTable.getId(), startDate, endDate);
    }

    protected SessionTable buildSessionTable(LocalDateTime startDate, LocalDateTime endDate) {
        return SessionTable.builder()
                .creationDate(DateUtils.currentDate())
                .topic(this.topicTable)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
