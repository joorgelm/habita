package br.com.jorge.habita.application.usecase.distribuicao;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class RealizarDistribuicaoOutput {
    //Todo: alterar para retornar as pessoas (membros) contempladas
    private List<RealizarDistribuicaoFamiliaOutput> familiasContempladas;

    @Data
    @Builder
    public  static class RealizarDistribuicaoMembroOutput {
        private String nome;
    }

    @Data
    @Builder
    public  static class RealizarDistribuicaoFamiliaOutput {
        private Long id;
        private List<RealizarDistribuicaoMembroOutput> membros;
    }
}
