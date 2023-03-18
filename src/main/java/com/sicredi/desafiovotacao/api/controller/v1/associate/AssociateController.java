package com.sicredi.desafiovotacao.api.controller.v1.associate;

import com.sicredi.desafiovotacao.api.controller.v1.associate.dto.AssociateRequest;
import com.sicredi.desafiovotacao.api.controller.v1.associate.dto.AssociateResponse;
import com.sicredi.desafiovotacao.driver.validatorapi.ValidatorApiService;
import com.sicredi.desafiovotacao.usecase.associate.AssociateService;
import com.sicredi.desafiovotacao.usecase.associate.enums.VoteStatusEnum;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

import static com.sicredi.desafiovotacao.common.MessagesConstants.VOTE_SUCCESSFULLY;

@RestController
@RequestMapping("/v1/associado")
@ApiOperation("API para operações do Associado")
public class AssociateController {

    private final AssociateService associateService;

    private final ValidatorApiService validatorApiService;

    @Autowired
    public AssociateController(AssociateService associateService, ValidatorApiService validatorApiService) {
        this.associateService = associateService;
        this.validatorApiService = validatorApiService;
    }

    @ApiOperation(value = "Computar voto do associado", notes = "Deve ser informado o Cpf ou o ID (se existente), " +
            "caso seja o primeiro voto do associado, o mesmo é cadastrado na base.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK - Voto computado com sucesso."),
            @ApiResponse(code = 400, message = "BAD REQUEST - Caso a opção de voto seja diferente de {s, n, sim, não, SIM, NÃO}"),
            @ApiResponse(code = 404, message = "NOT FOUND - Caso a sessão informada não seja encontrada."),
            @ApiResponse(code = 406, message = "NOT ACCEPTABLE - Caso a sessão esteja encerrada ou o " +
                    "voto viole a politica de voto único ou caso a validação de CPF falhe.")
    })
    @PostMapping
    public ResponseEntity<AssociateResponse> vote(@RequestBody @Valid AssociateRequest associateRequest) {

        if (Objects.isNull(associateRequest.getId())) {

            String associateId = associateService.executeVote(associateRequest);
            return new ResponseEntity<>(
                    AssociateResponse.of(associateId, VoteStatusEnum.ABLE, VOTE_SUCCESSFULLY), HttpStatus.OK);

        } else if (this.validatorApiService.isValidCpf(associateRequest.getCpf())) {

            String associateId = associateService.executeVote(associateRequest);
            return new ResponseEntity<>(
                    AssociateResponse.of(associateId, VoteStatusEnum.ABLE, VOTE_SUCCESSFULLY), HttpStatus.OK);

        }
        return new ResponseEntity<>(AssociateResponse.createUnableResponse(), HttpStatus.NOT_ACCEPTABLE);
    }
}
