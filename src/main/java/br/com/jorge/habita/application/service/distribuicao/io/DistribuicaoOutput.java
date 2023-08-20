package br.com.jorge.habita.application.service.distribuicao.io;

import br.com.jorge.habita.application.service.familia.io.FamiliaOutput;

import java.time.LocalDateTime;
import java.util.List;

public record DistribuicaoOutput(
        Long id,
        LocalDateTime dataDistribuicao,
        List<FamiliaOutput> familiasContempladas
) {}
