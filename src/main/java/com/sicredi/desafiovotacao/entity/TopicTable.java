package com.sicredi.desafiovotacao.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAUTA")
@ToString
public final class TopicTable {

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @Column(name = "ASSUNTO", nullable = false)
    private String subject;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime creationDate;

}
