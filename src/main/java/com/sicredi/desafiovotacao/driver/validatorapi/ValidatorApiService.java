package com.sicredi.desafiovotacao.driver.validatorapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidatorApiService {

    private ValidatorApiClient validatorApiClient;

    @Autowired
    public ValidatorApiService(ValidatorApiClient validatorApiClient) {
        this.validatorApiClient = validatorApiClient;
    }

    public boolean isValidCpf(String cpf) {
        return this.validatorApiClient.validateCpf(cpf);
    }
}
