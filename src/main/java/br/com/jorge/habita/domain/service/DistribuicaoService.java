package br.com.jorge.habita.domain.service;

import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public class DistribuicaoService {

    public List<Familia> homologarDistribuicao(List<Familia> familiasContempladas, Distribuicao distribuicao) {
//        familiasContempladas.forEach(familia -> familia.setDistribuicao(distribuicao));

        return familiasContempladas;
    }

    public Distribuicao gerarRegistroDeDistribuicao() {
        return Distribuicao
                .builder()
                .distribuicaoData(LocalDateTime.now())
                .build();
    }
}
