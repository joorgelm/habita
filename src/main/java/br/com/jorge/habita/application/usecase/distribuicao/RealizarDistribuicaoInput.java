package br.com.jorge.habita.application.usecase.distribuicao;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class RealizarDistribuicaoInput {

    @Positive(message = "Deve ser um inteiro positivo")
    private Integer qtdCasas;
}
