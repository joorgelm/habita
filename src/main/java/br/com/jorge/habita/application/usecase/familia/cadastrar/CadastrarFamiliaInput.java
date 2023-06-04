package br.com.jorge.habita.application.usecase.familia.cadastrar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CadastrarFamiliaInput {

    @NotNull(message = "Campo obrigatório")
    @Positive(message = "Renda inválida")
    private BigDecimal rendaTotal;

    @NotEmpty(message = "Campo obrigatório")
    private List<Membro> membros;

    @Data
    @Builder
    public static class Membro {

        @NotNull(message = "Campo obrigatório")
        @NotBlank(message = "Nome inválido")
        private String nome;

        @NotNull(message = "Campo obrigatório")
        @Positive(message = "Renda inválida")
        private Integer idade;
    }
}
