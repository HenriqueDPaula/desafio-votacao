package com.sicredi.desafiovotacao.usecase.associate;

import com.sicredi.desafiovotacao.api.controller.v1.associate.dto.AssociateRequest;
import com.sicredi.desafiovotacao.entity.AssociateTable;
import com.sicredi.desafiovotacao.entity.SessionTable;
import com.sicredi.desafiovotacao.usecase.associate.repository.AssociateRepository;
import com.sicredi.desafiovotacao.usecase.exception.ClosedSessionException;
import com.sicredi.desafiovotacao.usecase.exception.UniqueVoteViolationException;
import com.sicredi.desafiovotacao.usecase.session.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.sicredi.desafiovotacao.common.MessagesConstants.CLOSED_SESSION_ERROR;
import static com.sicredi.desafiovotacao.common.MessagesConstants.UNIQUE_VOTE_CONSTRAINT;

@Service
@Slf4j
public class AssociateService {

    private final SessionService sessionService;

    private final AssociateRepository associateRepository;

    @Autowired
    public AssociateService(SessionService sessionService, AssociateRepository associateRepository) {
        this.sessionService = sessionService;
        this.associateRepository = associateRepository;
    }

    public void executeVote(AssociateRequest associateRequest) {

        SessionTable session = this.sessionService.findById(associateRequest.getSessionId());

        if (this.sessionService.isSessionOpen(session)) {
            processAssociate(associateRequest, session);
            updateSession(session, associateRequest);
        } else {
            throw new ClosedSessionException(String.format(CLOSED_SESSION_ERROR, associateRequest.getSessionId()));
        }
    }

    private void processAssociate(AssociateRequest associateRequest, SessionTable session) {
        Optional<AssociateTable> associateTable = this.retrieveAssociate(associateRequest, session);

        if (associateTable.isEmpty()) {
            insertNewAssociate(associateRequest, session);
        } else {
            associateTable
                    .filter(associate -> this.isAssociateUniqueVote(associate, session))
                    .orElseThrow(
                            () -> new UniqueVoteViolationException(String.format(UNIQUE_VOTE_CONSTRAINT, session.getId())));
        }
    }

    private Optional<AssociateTable> retrieveAssociate(AssociateRequest associateRequest, SessionTable session) {
        return Optional.ofNullable(associateRequest.getId())
                .map(this.associateRepository::findById)
                .orElse(this.associateRepository.findByCpf(associateRequest.getCpf()));
    }

    private void insertNewAssociate(AssociateRequest associateRequest, SessionTable session) {
        AssociateTable associateTable = buildAssociateEntity(associateRequest, session);
        persistAssociate(associateTable);
    }

    private AssociateTable buildAssociateEntity(AssociateRequest associateRequest, SessionTable sessionTable) {
        return AssociateTable.builder()
                .session(sessionTable)
                .cpf(associateRequest.getCpf())
                .agree(associateRequest.getVoteDescription())
                .build();
    }

    private void persistAssociate(AssociateTable associateTable) {
        log.info("m=persistAssociate l=PERSIST_ASSOCIATE associateTable={}", associateTable);
        this.associateRepository.save(associateTable);
    }

    private void updateSession(SessionTable sessionTable, AssociateRequest associateRequest) {
        log.info("m=executeVote l=UPDATING_SESSION_VOTE sessionId={}", sessionTable.getId());

        sessionTable.appendVote(associateRequest.getVoteDescription());
        this.sessionService.updateSession(sessionTable);
    }

    private boolean isAssociateUniqueVote(AssociateTable associate, SessionTable session) {
        return !associate.getSession().getId().equals(session.getId());
    }

}
