package com.sicredi.desafiovotacao.usecase.topic;

import com.sicredi.desafiovotacao.api.controller.v1.topic.dto.TopicRequest;
import com.sicredi.desafiovotacao.entity.TopicTable;
import com.sicredi.desafiovotacao.usecase.exception.EntityNotFoundException;
import com.sicredi.desafiovotacao.usecase.topic.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.sicredi.desafiovotacao.common.MessagesConstants.TOPIC_NOT_FOUND;

@Service
@Slf4j
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Optional<String> createTopic(TopicRequest topicRequest) {
        return Optional.of(topicRequest)
                .map(this::buildTopicEntity)
                .map(this::persistTopic)
                .map(TopicTable::getId);
    }

    private TopicTable persistTopic(TopicTable topicTable) {
        log.info("m=persistTopic l=PERSIST_TOPIC topicTable={}", topicTable);
        return this.topicRepository.save(topicTable);
    }

    private TopicTable buildTopicEntity(TopicRequest topicRequest) {
        return TopicTable.builder()
                .subject(topicRequest.getSubject())
                .creationDate(LocalDateTime.now())
                .build();
    }

    public TopicTable findById(String topicId) {
        return this.topicRepository.findById(topicId).orElseThrow(() ->
                new EntityNotFoundException(String.format(TOPIC_NOT_FOUND, topicId)));
    }

}
