package br.com.jorge.habita.application.usecase.familia.cadastrar;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CadastrarFamiliaInput {

    @NotNull(message = "campo  obrigatório")
    private BigDecimal rendaTotal;

    @NotEmpty(message = "campo obrigatório")
    private List<Membro> membros;

    @Data
    @Builder
    public static class Membro {
        private String nome;
        private Integer idade;
    }
}
