package com.sicredi.desafiovotacao.usecase.topic;

import com.sicredi.desafiovotacao.api.controller.v1.topic.dto.TopicRequest;
import com.sicredi.desafiovotacao.common.DateUtils;
import com.sicredi.desafiovotacao.entity.TopicTable;
import com.sicredi.desafiovotacao.usecase.topic.TopicService;
import com.sicredi.desafiovotacao.usecase.topic.repository.TopicRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TopicServiceTest extends TopicServiceTestSupport{

    @Test
    public void shouldCreateTopicSuccess() {
        // when
        runScenario(buildTopicRequest());

        List<TopicTable> topicList = (List<TopicTable>) this.topicRepository.findAll();

        // then
        assertFalse(topicList.isEmpty());
        assertEquals("subject", topicList.get(0).getSubject());
    }

    @Test(expected = RuntimeException.class)
    public void shouldNotCreateTopicWhenThrowException() {
        // given
        doThrow(new RuntimeException())
                .when(topicRepository).save(any(TopicTable.class));

        // when
        Optional<String> result = topicService.createTopic(buildTopicRequest());

        // then
        assertTrue(result.isEmpty());
        verify(this.topicRepository, never()).save(any(TopicTable.class));

    }
}
