package br.com.jorge.habita.domain.entity;

import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaInput;
import net.datafaker.Faker;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;

public class MembroTest {

    private static final Faker faker = new Faker();

    @Test
    public void deveCriarMembroComValoresNulos() {
        Membro membro = new Membro();

        assertNotNull(membro);
    }

    @Test
    public void deveCriarMembroAPartirDoInputData() {
        String membroNome = faker.name().fullName();
        int membroIdade = faker.number().positive();

        Familia familiaMock = Mockito.mock(Familia.class);

        CadastrarFamiliaInput.Membro input = CadastrarFamiliaInput.Membro.builder()
                .nome(membroNome)
                .idade(membroIdade)
                .build();

        Membro membro = Membro.of(input, familiaMock);

        assertNotNull(membro);
    }

    @Test
    public void deveCriarMembroComTodosValoresNaoNulos() {

    }

    @Test
    public void deveFalharAoCriarMembroComNomeEmBranco() {

    }

    @Test
    public void deveFalharAoCriarMembroComIdadeNula() {

    }

    @Test
    public void deveFalharAoCriarMembroComIdadeNegativa() {

    }

    @Test
    public void deveFalharAoCriarMembroComIdNegativo() {

    }

    @Test
    public void deveFalharAoCriarMembroSemFamilia() {

    }
}