package br.com.jorge.habita.adapter.controller.familia;

import br.com.jorge.habita.application.service.familia.FamiliaService;
import br.com.jorge.habita.application.service.familia.io.FamiliaInput;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "familia")
public class FamiliaController {

    private final FamiliaService familiaService;

    public FamiliaController(FamiliaService familiaService) {
        this.familiaService = familiaService;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrar(@RequestBody @Valid FamiliaInput familiaInput) {
        familiaService.cadastrarFamilia(familiaInput);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
