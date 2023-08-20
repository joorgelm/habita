package br.com.jorge.habita.application.service.membro;

import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.domain.entity.Familia;

import java.util.List;

public interface MembroService {

    void cadastrarListaDeMembros(List<FamiliaInput.Membro> membros, Familia familia);
}
