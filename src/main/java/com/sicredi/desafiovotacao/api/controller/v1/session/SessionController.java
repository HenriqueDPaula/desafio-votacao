package com.sicredi.desafiovotacao.api.controller.v1.session;

import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionRequest;
import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionResponse;
import com.sicredi.desafiovotacao.usecase.session.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/sessao")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping
    public ResponseEntity<SessionResponse> create(@RequestBody @Valid SessionRequest sessionRequest) {
        SessionResponse sessionResponse = this.sessionService.createSession(sessionRequest)
                .orElse(SessionResponse.createEmptyResponse());
        return new ResponseEntity<>(sessionResponse, CREATED);
    }
}
