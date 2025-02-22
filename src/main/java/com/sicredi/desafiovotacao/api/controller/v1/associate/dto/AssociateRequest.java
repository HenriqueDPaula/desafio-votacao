package com.sicredi.desafiovotacao.api.controller.v1.associate.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sicredi.desafiovotacao.usecase.exception.InvalidVoteOptionException;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.sicredi.desafiovotacao.common.MessagesConstants.INVALID_VOTE_OPTION;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class AssociateRequest {

    @JsonProperty(value = "associado_id")
    @ApiModelProperty(notes = "Id do associado", example = "968c3966c51011edafa10242ac120002")
    private String id;

    @Size(min = 11,  max = 11, message = "CFP deve ter 11 caracteres, apenas números")
    @ApiModelProperty(notes = "CPF do associado", example = "12345678937")
    private String cpf;

    @NotBlank(message = "sessao_id não deve ser vazio")
    @JsonProperty(value = "sessao_id")
    @ApiModelProperty(notes = "ID da sessão dos votos", example = "968c3966c51011edafa10242ac120002", required = true)
    private String sessionId;

    @JsonProperty(value = "descricao_voto")
    @ApiModelProperty(notes = "Descrição dos votos (s, n, sim, não, SIM, NÃO",
            example = "SIM", required = true)
    private String voteDescription;

    @AssertTrue(message = "cpf ou associado_id deve ser preenchido")
    private boolean isVoteValid() {
        return !Objects.isNull(cpf) || !Objects.isNull(id);
    }

    /**
     * Para o voto ser opção válida, deve começar com 's' ou 'n'
     * @param vote
     */
    private void setVoteDescription(String vote) {

        Predicate<String> isVoteOptionValid = x -> startsWith(x, "s")
                || startsWith(x, "n");

        Optional.of(vote)
                .filter(isVoteOptionValid)
                .ifPresentOrElse(
                        (voteDescription) -> this.voteDescription = voteDescription,
                        () -> { throw new InvalidVoteOptionException(INVALID_VOTE_OPTION); }
                );
    }

    private boolean startsWith(String vote, String prefix) {
        return StringUtils.startsWithIgnoreCase(vote, prefix);
    }

}
