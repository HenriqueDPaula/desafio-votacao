package com.sicredi.desafiovotacao.api.controller.v1.topic;

import com.sicredi.desafiovotacao.api.controller.v1.topic.dto.TopicRequest;
import com.sicredi.desafiovotacao.api.controller.v1.topic.dto.TopicResponse;
import com.sicredi.desafiovotacao.usecase.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/v1/pauta")
public class TopicController {
    private static final String TOPIC_CREATE_MESSAGE = "Pauta %s criado com sucesso!";

    @PostMapping
    public ResponseEntity<TopicResponse> create(@RequestBody @Valid TopicRequest topicRequest) {

        return new ResponseEntity<>(
                TopicResponse.of("id", topicRequest.getCreationDate(),String.format(TOPIC_CREATE_MESSAGE, topicRequest.getSubject())),
                CREATED);

    }

}
