package br.com.jorge.habita.adapter.controller.distribuicao;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DistribuicaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void deveRealizarDistribuicao() throws Exception {

        ResultActions resposta = mockMvc.perform(get("/distribuicao")
                .contentType(MediaType.APPLICATION_JSON));

        resposta
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas[0].membros", Matchers.hasSize(5)));
    }
}
