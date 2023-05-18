package br.com.jorge.habita.application.usecase.familia.cadastrar;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class CadastrarFamiliaInput {
    //todo: colocar validacoes

    private BigDecimal rendaTotal;

    private List<Membro> membros;

    @Data
//    @NoArgsConstructor
    public static class Membro {
        private String nome;
        private Integer idade;
    }
}
