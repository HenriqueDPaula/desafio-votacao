package com.sicredi.desafiovotacao.api.controller.validatorapi;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
@ApiOperation("Validator API")
public class ValidatorApiController {

    @ApiOperation(value = "Valida CPF", notes = "True ou False aleatório")
    @ApiResponse(code = 200, message = "OK - CPF validado")
    @GetMapping(value = "/validar")
    public boolean isCpfValid(@RequestParam String cpf) {
        return new Random().nextBoolean();
    }

}
