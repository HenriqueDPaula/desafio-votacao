package com.sicredi.desafiovotacao.api.controller.v1.topic;

import com.sicredi.desafiovotacao.api.controller.v1.topic.dto.TopicRequest;
import com.sicredi.desafiovotacao.api.controller.v1.topic.dto.TopicResponse;
import com.sicredi.desafiovotacao.usecase.topic.TopicService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;

import static com.sicredi.desafiovotacao.common.MessagesConstants.TOPIC_CREATE_MESSAGE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/pauta")
@ApiOperation("API para operações da Pauta")
public class TopicController {

    private final TopicService topicService;

    private static final String EMPTY = "";

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @ApiOperation(value = "Criar uma pauta de votação", notes = "Deve ser informado o assunto")
    @ApiResponse(code = 201, message = "CREATED - Pauta criada com sucesso")
    @PostMapping
    public ResponseEntity<TopicResponse> create(@RequestBody @Valid TopicRequest topicRequest) {

        String id = topicService.createTopic(topicRequest).orElse(EMPTY);

        return new ResponseEntity<>(
                TopicResponse.of(id, LocalDateTime.now(), String.format(TOPIC_CREATE_MESSAGE, topicRequest.getSubject())),
                CREATED);

    }

}
