package com.sicredi.desafiovotacao.api.controller.validatorapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class ValidatorApiController {

    @GetMapping(value = "/validar")
    public boolean isCpfValid(@RequestParam String cpf) {
        Random random = new Random();
        return random.nextBoolean();
    }

}
