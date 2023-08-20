package br.com.jorge.habita.adapter.controller.distribuicao;

import br.com.jorge.habita.application.repository.DistribuicaoRepository;
import br.com.jorge.habita.application.repository.FamiliaRepository;
import br.com.jorge.habita.application.service.distribuicao.io.DistribuicaoInput;
import br.com.jorge.habita.domain.entity.Distribuicao;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DistribuicaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private DistribuicaoRepository distribuicaoRepository;

    @Test
    public void deveRealizarDistribuicao() throws Exception {
        DistribuicaoInput input = DistribuicaoInput.builder().qtdCasas(1).build();

        ResultActions resposta = mockMvc.perform(post("/distribuicao")
                .content(new Gson().toJson( input))
                .contentType(MediaType.APPLICATION_JSON));

        resposta
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas[0].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas[0].membros", Matchers.hasSize(5)));
    }

    @Test
    public void deveRealizarDistribuicaoDeVariasCasas() throws Exception {
        DistribuicaoInput input = DistribuicaoInput.builder().qtdCasas(2).build();

        ResultActions resposta = mockMvc.perform(post("/distribuicao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta
                .andExpect(status().is2xxSuccessful())
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas[0].id", Matchers.is(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas[0].membros", Matchers.hasSize(5)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.familiasContempladas[1].membros", Matchers.hasSize(5)));
    }

    @Test
    public void deveFalharQuandoNaoHouverFamiliasElegiveis() throws Exception {
        marcarFamiliasComoDistribuidas();

        DistribuicaoInput input = DistribuicaoInput.builder().qtdCasas(3).build();

        ResultActions resposta = mockMvc.perform(post("/distribuicao")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta
                .andExpect(status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$")
                        .value("O número de famílias solicitado é maior do que o número de famílias elegíveis cadastradas."));
    }

    private void marcarFamiliasComoDistribuidas() {
        Distribuicao distribuicao = distribuicaoRepository.save(
                new Distribuicao()
        );
        familiaRepository.saveAll(
                familiaRepository.findAll()
                        .stream()
                        .peek(familia -> familia.setDistribuicao(distribuicao))
                        .toList()
        );
    }
}
