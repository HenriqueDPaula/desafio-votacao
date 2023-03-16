package com.sicredi.desafiovotacao.usecase.session.repository;

import com.sicredi.desafiovotacao.entity.SessionTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends CrudRepository<SessionTable, String> {
}
