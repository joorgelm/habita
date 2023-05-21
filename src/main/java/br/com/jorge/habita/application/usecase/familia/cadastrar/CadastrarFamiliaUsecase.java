package br.com.jorge.habita.application.usecase.familia.cadastrar;

import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.adapter.repository.MembroRepository;
import br.com.jorge.habita.application.usecase.familia.cadastrar.converter.CadastrarFamiliaConverter;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import br.com.jorge.habita.domain.exception.RendaInvalidaException;
import jakarta.transaction.Transactional;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public class CadastrarFamiliaUsecase {

    private MembroRepository membroRepository;
    private FamiliaRepository familiaRepository;

    @Transactional
    public void cadastrarFamilia(CadastrarFamiliaInput cadastrarFamiliaInput) {
        validarDados(cadastrarFamiliaInput);
        Familia familiaCadastrada = familiaRepository.save(CadastrarFamiliaConverter.converter(cadastrarFamiliaInput));
        familiaCadastrada.setMembros(cadastrarMembros(cadastrarFamiliaInput.getMembros(), familiaCadastrada));
    }

    private void validarDados(CadastrarFamiliaInput cadastrarFamiliaInput) {
        if (cadastrarFamiliaInput.getRendaTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw  new RendaInvalidaException();
        }
    }

    private List<Membro> cadastrarMembros(List<CadastrarFamiliaInput.Membro> inputMembros, Familia familia) {
        List<Membro> membros = inputMembros.stream()
                .map(membro -> CadastrarFamiliaConverter.converter(membro, familia))
                .toList();
        return  membroRepository.saveAll(membros);
    }
}
