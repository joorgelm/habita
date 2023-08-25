package br.com.jorge.habita.application.service.distribuicao;

import br.com.jorge.habita.application.service.distribuicao.io.DistribuicaoOutput;
import br.com.jorge.habita.domain.entity.Distribuicao;

public interface DistribuicaoService {

    Distribuicao gerarRegistroDistribuicao();

    DistribuicaoOutput realizarDistribuicaoDeCasas(int qtdCasas);
}
