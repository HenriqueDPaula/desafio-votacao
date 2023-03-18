package com.sicredi.desafiovotacao.usecase.topic;

import com.sicredi.desafiovotacao.api.controller.v1.topic.dto.TopicRequest;
import com.sicredi.desafiovotacao.common.DateUtils;
import com.sicredi.desafiovotacao.entity.TopicTable;
import com.sicredi.desafiovotacao.usecase.topic.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class TopicServiceTestSupport {

    @SpyBean
    protected TopicRepository topicRepository;

    @Autowired
    protected TopicService topicService;

    protected void runScenario(TopicRequest topicRequest) {
        this.topicService.createTopic(topicRequest);
    }

    public TopicRequest buildTopicRequest() {
        return new TopicRequest("subject", null);
    }

    protected TopicTable buildTopicTable() {
        TopicTable topicTable = new TopicTable();
        topicTable.setId("1");
        topicTable.setSubject("subject");
        topicTable.setCreationDate(DateUtils.currentDate());
        return topicTable;
    }
}
