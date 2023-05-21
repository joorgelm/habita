package br.com.jorge.habita.adapter.controller.familia;

import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaInput;
import br.com.jorge.habita.application.usecase.familia.cadastrar.CadastrarFamiliaUsecase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "familia")
public class FamiliaController {

    private CadastrarFamiliaUsecase cadastrarFamiliaUsecase;

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid CadastrarFamiliaInput cadastrarFamiliaInput) {
        cadastrarFamiliaUsecase.cadastrarFamilia(cadastrarFamiliaInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
