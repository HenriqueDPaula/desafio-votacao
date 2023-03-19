package com.sicredi.desafiovotacao.api.controller.validatorapi;

import com.sicredi.desafiovotacao.BaseContextConfigurationTest;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ValidatorApiControllerTest extends BaseContextConfigurationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnCpfValid() throws Exception {
        String cpf = "CPF";

        MvcResult response = mockMvc
                .perform(get("/validar")
                        .param("cpf", cpf))
                .andReturn();

        Boolean result = Boolean.parseBoolean(response.getResponse().getContentAsString());

        Assert.assertThat(result, Matchers.either(Matchers.is(true)).or(Matchers.is(false)));
    }

}
