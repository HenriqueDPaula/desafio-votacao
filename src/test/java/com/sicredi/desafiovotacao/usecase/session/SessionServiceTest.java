package com.sicredi.desafiovotacao.usecase.session;

import com.sicredi.desafiovotacao.BaseContextConfigurationTest;
import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionRequest;
import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionResponse;
import com.sicredi.desafiovotacao.common.DateUtils;
import com.sicredi.desafiovotacao.entity.SessionTable;
import com.sicredi.desafiovotacao.entity.TopicTable;
import com.sicredi.desafiovotacao.usecase.exception.EntityNotFoundException;
import com.sicredi.desafiovotacao.usecase.exception.InvalidDateException;
import com.sicredi.desafiovotacao.usecase.session.repository.SessionRepository;
import com.sicredi.desafiovotacao.usecase.topic.repository.TopicRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.sicredi.desafiovotacao.common.MessagesConstants.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mockStatic;

public class SessionServiceTest extends BaseContextConfigurationTest {

    @SpyBean
    private SessionResponse sessionResponse;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private TopicRepository topicRepository;

    private TopicTable topicTable;

    private boolean isSessionOpen;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void before() {
        topicTable = topicRepository.save(TopicTable.builder()
                .creationDate(DateUtils.currentDate())
                .subject("subject")
                .build());
    }

    @Test
    public void shouldCreateSessionSuccessful() {

        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);
        LocalDateTime startDate = currentDate.plus(1, ChronoUnit.MINUTES);
        LocalDateTime endDate = startDate.plus(1, ChronoUnit.MINUTES);

        runCreateSession(currentDate, startDate, endDate);

        assertNotNull(sessionResponse);
        assertEquals(SESSION_CREATE_MESSAGE, sessionResponse.getMessage());
    }

    @Test
    public void shouldCreateSessionWithoutStartDate() {
        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);
        LocalDateTime endDate = currentDate.plus(5, ChronoUnit.MINUTES);

        runCreateSession(currentDate, null, endDate);

        assertNotNull(sessionResponse);
        assertEquals(SESSION_CREATE_MESSAGE, sessionResponse.getMessage());
        assertEquals(currentDate.plus(1, ChronoUnit.MINUTES), sessionResponse.getStartDate());
    }

    @Test
    public void shouldCreateSessionWithoutEndDate() {
        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);
        LocalDateTime startDate = currentDate.plus(1, ChronoUnit.MINUTES);

        runCreateSession(currentDate, startDate, null);

        assertNotNull(sessionResponse);
        assertEquals(SESSION_CREATE_MESSAGE, sessionResponse.getMessage());
        assertEquals(startDate.plus(1, ChronoUnit.MINUTES), sessionResponse.getEndDate());
    }

    @Test
    public void shouldCreateSessionWithoutAnyDate() {
        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);

        runCreateSession(currentDate, null, null);

        assertNotNull(sessionResponse);
        assertEquals(SESSION_CREATE_MESSAGE, sessionResponse.getMessage());
        assertEquals(currentDate.plus(1, ChronoUnit.MINUTES), sessionResponse.getStartDate());
        assertEquals(sessionResponse.getStartDate().plus(1, ChronoUnit.MINUTES), sessionResponse.getEndDate());
    }

    @Test
    public void shouldThrowInvalidDateExceptionWhenStartDateBeforeCurrentDate() {

        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage(INVALID_DATE_START);

        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);
        LocalDateTime startDate = currentDate.minus(1, ChronoUnit.MINUTES);

        runCreateSession(currentDate, startDate, null);

        assertNull(sessionResponse);
    }

    @Test
    public void shouldThrowInvalidDateExceptionWhenEndDateBeforeStartDate() {

        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage(INVALID_DATE_BETWEEN);

        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);
        LocalDateTime startDate = currentDate.plus(1, ChronoUnit.MINUTES);
        LocalDateTime endDate = startDate.minus(1, ChronoUnit.MINUTES);

        runCreateSession(currentDate, startDate, endDate);

        assertNull(sessionResponse);
    }

    @Test
    public void shouldThrowInvalidDateExceptionWhenEndDateBeforeCurrentDate() {

        expectedException.expect(InvalidDateException.class);
        expectedException.expectMessage(INVALID_DATE_BETWEEN);

        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);
        LocalDateTime startDate = currentDate.plus(30, ChronoUnit.SECONDS);
        LocalDateTime endDate = currentDate.minus(1, ChronoUnit.MINUTES);

        runCreateSession(currentDate, startDate, endDate);

        assertNull(sessionResponse);
    }

    @Test
    public void shouldUpdateSessionFavorSuccessful() {
        SessionTable sessionTable = buildSessionTable(DateUtils.currentDate(), DateUtils.currentDate());
        sessionRepository.save(sessionTable);

        sessionTable.appendVote("s");
        sessionTable.appendVote("S");
        sessionTable.appendVote("sim");
        sessionTable.appendVote("SIM");

        sessionService.updateSession(sessionTable);

        assertEquals(4, sessionTable.getCountFavor());
        assertEquals(0, sessionTable.getCountAgainst());
    }

    @Test
    public void shouldUpdateSessionAgainstSuccessful() {
        SessionTable sessionTable = buildSessionTable(DateUtils.currentDate(), DateUtils.currentDate());
        sessionRepository.save(sessionTable);

        sessionTable.appendVote("n");
        sessionTable.appendVote("N");
        sessionTable.appendVote("não");
        sessionTable.appendVote("NÃO");

        sessionService.updateSession(sessionTable);

        assertEquals(0, sessionTable.getCountFavor());
        assertEquals(4, sessionTable.getCountAgainst());
    }

    @Test
    public void shouldSessionBeOpened() {
        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);
        LocalDateTime startDate = currentDate.minus(30, ChronoUnit.SECONDS);
        LocalDateTime endDate = startDate.plus(1, ChronoUnit.MINUTES);

        SessionTable sessionTable = buildSessionTable(startDate, endDate);

        runIsSessionOpen(sessionTable, currentDate);

        assertTrue(isSessionOpen);
    }

    @Test
    public void shouldSessionBeClosed() {
        LocalDateTime currentDate = LocalDateTime.of(2023, 3, 20, 12, 30, 30);
        LocalDateTime startDate = LocalDateTime.of(2023, 3, 20, 12, 20, 30);
        LocalDateTime endDate = startDate.plus(5, ChronoUnit.MINUTES);

        SessionTable sessionTable = buildSessionTable(startDate, endDate);

        runIsSessionOpen(sessionTable, currentDate);

        assertFalse(isSessionOpen);
    }

    @Test
    public void shouldFindByIdSuccessful() {
        SessionTable sessionTable = buildSessionTable(DateUtils.currentDate(), DateUtils.currentDate());
        sessionRepository.save(sessionTable);

        SessionTable sessionById = runFindById(sessionTable.getId());

        assertEquals(sessionTable.getId(), sessionById.getId());
    }

    @Test
    public void shouldThrowEntityNotFoundExceptionWhenSessionNotFound() {

        String sessionId = "a4f5555h33";

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage(String.format(SESSION_NOT_FOUND, sessionId));

        SessionTable sessionById = runFindById(sessionId);

        assertNull(sessionById);
    }

    private void runCreateSession(LocalDateTime currentDate, LocalDateTime startDate, LocalDateTime endDate) {
        // Necessário mockar a hora do sistema
        try (MockedStatic<DateUtils> dateMock = mockStatic(DateUtils.class)) {
            dateMock.when(DateUtils::currentDate)
                    .thenReturn(currentDate);

            var sessionRequest = buildSessionRequest(startDate, endDate);

            this.sessionService.createSession(sessionRequest)
                    .ifPresent(response -> this.sessionResponse = response);
        }
    }

    private void runIsSessionOpen(SessionTable sessionTable, LocalDateTime currentDate) {
        try (MockedStatic<DateUtils> dateMock = mockStatic(DateUtils.class)) {
            dateMock.when(DateUtils::currentDate)
                    .thenReturn(currentDate);
            isSessionOpen = sessionTable.isOpen();
        }
    }

    private SessionTable runFindById(String id) {
        return this.sessionService.findById(id);
    }

    private SessionRequest buildSessionRequest(LocalDateTime startDate, LocalDateTime endDate) {
        return new SessionRequest(this.topicTable.getId(), startDate, endDate);
    }

    private SessionTable buildSessionTable(LocalDateTime startDate, LocalDateTime endDate) {
        return SessionTable.builder()
                .creationDate(DateUtils.currentDate())
                .topic(this.topicTable)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
