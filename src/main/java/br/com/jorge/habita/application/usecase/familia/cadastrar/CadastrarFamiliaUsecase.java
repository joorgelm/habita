package br.com.jorge.habita.application.usecase.familia.cadastrar;

import br.com.jorge.habita.adapter.repository.FamiliaRepository;
import br.com.jorge.habita.adapter.repository.MembroRepository;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import br.com.jorge.habita.domain.strategy.CriterioAvalicaoStrategy;
import jakarta.transaction.Transactional;
import lombok.Builder;

import java.util.List;

@Builder
public class CadastrarFamiliaUsecase {

    private MembroRepository membroRepository;
    private FamiliaRepository familiaRepository;
    private List<CriterioAvalicaoStrategy> criterioAvalicaoStrategies;

    @Transactional
    public void cadastrarFamilia(CadastrarFamiliaInput cadastrarFamiliaInput) {
        //todo: colocar em um converter
        Familia familia = Familia.builder()
                .rendaTotal(cadastrarFamiliaInput.getRendaTotal())
                .build();

        Familia familiaCadastrada = familiaRepository.save(familia);
        familiaCadastrada.setMembros(cadastrarMembros(cadastrarFamiliaInput.getMembros(), familiaCadastrada));
        familiaCadastrada.setPontuacao(obterPontuacao(familiaCadastrada));
    }

    private List<Membro> cadastrarMembros(List<CadastrarFamiliaInput.Membro> membros, Familia familia) {
        // todo: validar se a lista esta vazia
        List<Membro> pessoas = membros.stream()
                .map(membro -> membroConverter(membro, familia))
                .toList();
        return  membroRepository.saveAll(pessoas);
    }

    private static Membro membroConverter(CadastrarFamiliaInput.Membro membro, Familia familia) {
        return Membro
                .builder()
                .familia(familia)
                .idade(membro.getIdade())
                .nome(membro.getNome())
                .build();
    }

    private Integer obterPontuacao(Familia familia) {
        return  criterioAvalicaoStrategies
                .stream()
                .map(criterioAvalicaoStrategy -> criterioAvalicaoStrategy.obterPontuacao(familia))
                .reduce(0, Integer::sum);
    }
}
