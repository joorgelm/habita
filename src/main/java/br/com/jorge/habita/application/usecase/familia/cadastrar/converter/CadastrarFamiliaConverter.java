package br.com.jorge.habita.application.usecase.familia.cadastrar.converter;

import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaInput;
import br.com.jorge.habita.domain.entity.Familia;
import br.com.jorge.habita.domain.entity.Membro;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class CadastrarFamiliaConverter { //todo: remover
    public static Familia converter(CadastrarFamiliaInput input) {
        return null;
    }

    public static Membro converter(CadastrarFamiliaInput.Membro input , Familia familia) {
        return Membro.of(input, familia);
    }
}
