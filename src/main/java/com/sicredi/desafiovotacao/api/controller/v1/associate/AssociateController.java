package com.sicredi.desafiovotacao.api.controller.v1.associate;

import com.sicredi.desafiovotacao.api.controller.v1.associate.dto.AssociateRequest;
import com.sicredi.desafiovotacao.api.controller.v1.associate.dto.AssociateResponse;
import com.sicredi.desafiovotacao.driver.validatorapi.ValidatorApiService;
import com.sicredi.desafiovotacao.usecase.associate.AssociateService;
import com.sicredi.desafiovotacao.usecase.associate.enums.VoteStatusEnum;
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
public class AssociateController {

    private final AssociateService associateService;

    private final ValidatorApiService validatorApiService;

    @Autowired
    public AssociateController(AssociateService associateService, ValidatorApiService validatorApiService) {
        this.associateService = associateService;
        this.validatorApiService = validatorApiService;
    }

    @PostMapping
    public ResponseEntity<AssociateResponse> vote(@RequestBody @Valid AssociateRequest associateRequest) {

        if (Objects.isNull(associateRequest.getId())) {

            associateService.executeVote(associateRequest);
            return new ResponseEntity<>(AssociateResponse.of(VoteStatusEnum.ABLE, VOTE_SUCCESSFULLY), HttpStatus.OK);

        } else if (this.validatorApiService.isValidCpf(associateRequest.getCpf())) {

            associateService.executeVote(associateRequest);
            return new ResponseEntity<>(AssociateResponse.of(VoteStatusEnum.ABLE, VOTE_SUCCESSFULLY), HttpStatus.OK);

        }
        return new ResponseEntity<>(AssociateResponse.createUnableResponse(), HttpStatus.NOT_ACCEPTABLE);
    }
}
