package com.sicredi.desafiovotacao.driver.validatorapi;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "validatorClient", url = "${validator-api.url}")
public interface ValidatorApiClient {

    @GetMapping(value = "/validar")
    boolean validateCpf(@RequestParam(value = "cpf") String cpf);
}
