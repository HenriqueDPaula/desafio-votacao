package com.sicredi.desafiovotacao.entity;

import com.sicredi.desafiovotacao.api.controller.v1.session.dto.SessionRequest;
import com.sicredi.desafiovotacao.common.DateUtils;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "SESSAO")
public class SessionTable {

    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @ManyToOne
    @JoinColumn(name = "PAUTA_ID")
    private TopicTable topic;

    @Column(name = "DATA_CRIACAO", nullable = false)
    private LocalDateTime creationDate;

    @Column(name = "DATA_INICIO_SESSAO", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "DATA_FIM_SESSAO", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "CONTAGEM_VOTOS_FAVOR", nullable = false)
    @Min(0)
    private int countFavor;

    @Column(name = "CONTAGEM_VOTOS_CONTRA", nullable = false)
    @Min(0)
    private int countAgainst;

    public static SessionTable of(TopicTable topicTable, SessionRequest sessionRequest) {
        return SessionTable.builder()
                .topic(topicTable)
                .creationDate(DateUtils.currentDate())
                .startDate(sessionRequest.getStartDate())
                .endDate(sessionRequest.getEndDate())
                .build();
    }

    public boolean isOpen() {
        var currentDate = DateUtils.currentDate();
        return startDate.isBefore(currentDate) && endDate.isAfter(currentDate);
    }

    public void appendVote(String voteDescription) {
        if (isVoteInFavor(voteDescription)) {
            this.countFavor ++;
        } else {
            this.countAgainst ++;
        }
    }

    private boolean isVoteInFavor(String voteDescription) {
        return voteDescription.toLowerCase().startsWith("s");
    }

}
