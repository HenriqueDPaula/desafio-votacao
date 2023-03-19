package com.sicredi.desafiovotacao.entity;

import com.sicredi.desafiovotacao.api.controller.v1.associate.dto.AssociateRequest;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ASSOCIADO")
public class AssociateTable {

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "SESSAO_ID")
    private SessionTable session;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "DE_VOTO", nullable = false)
    private String agree;

    public static AssociateTable of(AssociateRequest associateRequest, SessionTable sessionTable) {
        return AssociateTable.builder()
                .session(sessionTable)
                .cpf(associateRequest.getCpf())
                .agree(associateRequest.getVoteDescription())
                .build();
    }

    public boolean isUniqueVote(SessionTable session) {
        return !this.session.getId().equals(session.getId());
    }
}
