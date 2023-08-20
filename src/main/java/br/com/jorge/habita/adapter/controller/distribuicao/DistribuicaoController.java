package br.com.jorge.habita.adapter.controller.distribuicao;

import br.com.jorge.habita.application.service.distribuicao.DistribuicaoService;
import br.com.jorge.habita.application.service.distribuicao.io.DistribuicaoInput;
import br.com.jorge.habita.application.service.distribuicao.io.DistribuicaoOutput;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "distribuicao")
public class DistribuicaoController {

    private final DistribuicaoService distribuicaoService;

    public DistribuicaoController(DistribuicaoService distribuicaoService) {
        this.distribuicaoService = distribuicaoService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<DistribuicaoOutput> realizarDistribuicao(
            @RequestBody @Valid DistribuicaoInput distribuicaoInput
    ) {
        DistribuicaoOutput distribuicaoOutput = distribuicaoService.realizarDistribuicaoDeCasas(distribuicaoInput.getQtdCasas());
        return new ResponseEntity<>(distribuicaoOutput, HttpStatus.OK);
    }
}
