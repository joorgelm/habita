package br.com.jorge.habita.application.service.membro;

import br.com.jorge.habita.application.repository.MembroRepository;
import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembroServiceImpl implements MembroService {

    private final MembroRepository membroRepository;

    public MembroServiceImpl(MembroRepository membroRepository) {
        this.membroRepository = membroRepository;
    }

    @Transactional
    @Override
    public void cadastrarListaDeMembros(List<FamiliaInput.Membro> membros, Familia familia) {

        List<Membro> membrosToSave = membros
                .stream()
                .map(membro -> Membro.of(membro, familia))
                .toList();

        membroRepository.saveAll(membrosToSave);
    }
}
