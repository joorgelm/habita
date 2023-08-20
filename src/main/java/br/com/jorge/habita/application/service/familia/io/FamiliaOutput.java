package br.com.jorge.habita.application.service.familia.io;

import br.com.jorge.habita.application.service.membro.io.MembroOutput;

import java.util.List;

public record FamiliaOutput(
        Long id,
        List<MembroOutput> membros
) {}
