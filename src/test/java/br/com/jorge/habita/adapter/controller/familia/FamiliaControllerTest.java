package br.com.jorge.habita.adapter.controller.familia;

import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaInput;
import com.google.gson.Gson;
import net.datafaker.Faker;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class FamiliaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static final Faker faker = new Faker();

    @Test
    public void deveCadastrarFamilia() throws Exception {
        CadastrarFamiliaInput input = criarInput();

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta.andExpect(status().isCreated());
    }
    @Test
    public void deveFalharAoCadastrarFamiliaSemRenda() throws Exception {
        CadastrarFamiliaInput input = criarInput();
        input.setRendaTotal(BigDecimal.ZERO);

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rendaTotal").value("Renda inválida"));
    }

    @Test
    public void deveFalharAoCadastrarFamiliaComRendaNula() throws Exception {
        CadastrarFamiliaInput input = criarInput();
        input.setRendaTotal(null);

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rendaTotal").value("Campo obrigatório"));
    }

    @Test
    public void deveFalharAoCadastrarFamiliaSemMembros() throws Exception {
        CadastrarFamiliaInput input = criarInput();
        input.setMembros(List.of());

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.membros").value("Campo obrigatório"));
    }

    private CadastrarFamiliaInput criarInput() {
        return CadastrarFamiliaInput
                .builder()
                .rendaTotal(BigDecimal.valueOf(faker.number().numberBetween(100L, 1600L)))
                .membros(criarListaDeMembros(faker.number().numberBetween(2, 7)))
                .build();
    }

    private static List<CadastrarFamiliaInput.Membro> criarListaDeMembros(int tamanho) {
        return IntStream.rangeClosed(1, tamanho)
                .boxed()
                .map(integer -> criarMembro())
                .toList();
    }

    private static CadastrarFamiliaInput.Membro criarMembro() {
        return CadastrarFamiliaInput.Membro
                .builder()
                .nome(faker.name().fullName())
                .idade(faker.number().numberBetween(1, 99))
                .build();
    }
}
