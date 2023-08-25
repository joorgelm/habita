package br.com.jorge.habita.application.service.familia;

import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.domain.entity.Distribuicao;
import br.com.jorge.habita.domain.entity.Familia;

import java.util.List;

public interface FamiliaService {

    void cadastrarFamilia(FamiliaInput familiaInput);

    List<Familia> buscarFamiliasContempladas(Integer quantidadeCasas);

    void homologarFamiliaContemplada(Familia familia, Distribuicao distribuicao);
}
