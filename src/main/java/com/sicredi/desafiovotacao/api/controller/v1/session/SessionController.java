package com.sicredi.desafiovotacao.api.controller.v1.session;

import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionRequest;
import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionResponse;
import com.sicredi.desafiovotacao.usecase.session.SessionService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;
import java.util.function.Predicate;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/sessao")
@ApiOperation("API para operações da Sessão")
public class SessionController {

    private final SessionService sessionService;

    @Autowired
    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @ApiOperation(value = "Criar sessão de votos", notes = "ID da Pauta deve ser informado")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED - Sessão criada com sucesso"),
            @ApiResponse(code = 400, message = "BAD REQUEST - Datas inválidas"),
            @ApiResponse(code = 404, message = "NOT FOUND - Caso a Pauta não seja encontrada na base")
    })
    @PostMapping
    public ResponseEntity<SessionResponse> create(@RequestBody @Valid SessionRequest sessionRequest) {
        SessionResponse sessionResponse = this.sessionService.createSession(sessionRequest)
                .orElse(SessionResponse.createEmptyResponse());

        Optional.of(sessionResponse.getSessionId())
                .filter(Predicate.not(String::isBlank))
                .ifPresent(id -> {
                    sessionResponse.add(linkTo(methodOn(this.getClass())
                            .getResult(id)).withRel("resultado"));
                });

        return new ResponseEntity<>(sessionResponse, CREATED);
    }

    @GetMapping("/resultado/{id}")
    public ResponseEntity<SessionResponse.SessionResponseResult> getResult(@PathVariable(value = "id") String id) {

        SessionResponse.SessionResponseResult sessionResponseResult =
                this.sessionService.getResult(id);

        return new ResponseEntity<>(sessionResponseResult, HttpStatus.OK);
    }
}
