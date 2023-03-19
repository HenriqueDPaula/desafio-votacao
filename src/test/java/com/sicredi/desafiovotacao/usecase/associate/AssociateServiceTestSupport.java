package com.sicredi.desafiovotacao.usecase.associate;

import com.sicredi.desafiovotacao.entity.SessionTable;
import com.sicredi.desafiovotacao.entity.TopicTable;
import org.springframework.boot.test.mock.mockito.SpyBean;

public class AssociateServiceTestSupport {

    @SpyBean
    protected AssociateService associateService;

    protected TopicTable topicTable;

    protected SessionTable sessionTable;

    protected void insertDependenciesEntities() {

    }


}
