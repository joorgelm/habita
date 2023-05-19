package br.com.jorge.habita.adapter.controller.familia;

import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaInput;
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

        CadastrarFamiliaInput.Membro pauloNogueira = criarMembro("Paulo Nogueira", 50);


        CadastrarFamiliaInput input = CadastrarFamiliaInput
                .builder()
                .rendaTotal(BigDecimal.valueOf(900L))
                .membros(List.of(pauloNogueira))
                .build();

        ResultActions resposta = mockMvc.perform(MockMvcRequestBuilders.post("/familia")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson( input)));
    }

    private static CadastrarFamiliaInput.Membro criarMembro(String pauloNogueira, int i) {


        return CadastrarFamiliaInput.Membro.builder().nome("Paulo Nogueira").idade(50).build();
    }
}
