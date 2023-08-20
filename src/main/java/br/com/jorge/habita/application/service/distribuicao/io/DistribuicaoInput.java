package br.com.jorge.habita.application.service.distribuicao.io;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Builder
@Data
@Jacksonized
public class DistribuicaoInput {

    @Positive(message = "Deve ser um inteiro positivo")
    @NotNull
    private Integer qtdCasas;
}
