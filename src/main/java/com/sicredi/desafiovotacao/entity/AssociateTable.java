package com.sicredi.desafiovotacao.entity;

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
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "SESSAO_ID")
    private SessionTable session;

    @Column(name = "CPF")
    private String cpf;

    @Column(name = "DE_VOTO", nullable = false)
    private String agree;

}
