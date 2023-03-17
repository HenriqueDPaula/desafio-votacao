package com.sicredi.desafiovotacao.usecase.associate.repository;

import com.sicredi.desafiovotacao.entity.AssociateTable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssociateRepository extends CrudRepository<AssociateTable, String> {

    Optional<AssociateTable> findByCpf(String cpf);

    Optional<AssociateTable> findByIdAndSessionId(String id, String sessionId);

}
