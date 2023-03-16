package com.sicredi.desafiovotacao.usecase.topic.repository;

import com.sicredi.desafiovotacao.entity.TopicTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<TopicTable, String> {
}
