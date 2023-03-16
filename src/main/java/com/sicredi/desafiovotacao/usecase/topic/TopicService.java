package com.sicredi.desafiovotacao.usecase.topic;

import com.sicredi.desafiovotacao.api.controller.v1.topic.dto.TopicRequest;
import com.sicredi.desafiovotacao.driver.kafka.producer.TopicKafkaProducer;
import com.sicredi.desafiovotacao.entity.TopicTable;
import com.sicredi.desafiovotacao.usecase.topic.repository.TopicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class TopicService {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Transactional
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
                .creationDate(topicRequest.getCreationDate())
                .build();
    }

}
