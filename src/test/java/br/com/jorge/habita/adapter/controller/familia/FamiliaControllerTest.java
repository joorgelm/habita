package br.com.jorge.habita.adapter.controller.familia;

import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import com.google.gson.Gson;
import net.datafaker.Faker;
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
        FamiliaInput input = criarInput();

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta.andExpect(status().isCreated());
    }
    @Test
    public void deveFalharAoCadastrarFamiliaSemRenda() throws Exception {
        FamiliaInput input = criarInput();
        input.setRendaTotal(BigDecimal.ZERO);

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rendaTotal").value("Renda inválida"));
    }

    @Test
    public void deveFalharAoCadastrarFamiliaComRendaNula() throws Exception {
        FamiliaInput input = criarInput();
        input.setRendaTotal(null);

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rendaTotal").value("Campo obrigatório"));
    }

    @Test
    public void deveFalharAoCadastrarFamiliaSemMembros() throws Exception {
        FamiliaInput input = criarInput();
        input.setMembros(List.of());

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));

        resposta.andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.membros").value("Campo obrigatório"));
    }

    private FamiliaInput criarInput() {
        return FamiliaInput
                .builder()
                .rendaTotal(BigDecimal.valueOf(faker.number().numberBetween(100L, 1600L)))
                .membros(criarListaDeMembros(faker.number().numberBetween(2, 7)))
                .build();
    }

    private static List<FamiliaInput.Membro> criarListaDeMembros(int tamanho) {
        return IntStream.rangeClosed(1, tamanho)
                .boxed()
                .map(integer -> criarMembro())
                .toList();
    }

    private static FamiliaInput.Membro criarMembro() {
        return FamiliaInput.Membro
                .builder()
                .nome(faker.name().fullName())
                .idade(faker.number().numberBetween(1, 99))
                .build();
    }
}
