package br.com.jorge.habita.adapter.controller.distribuicao;

import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoInput;
import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoOutput;
import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoUsecase;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "distribuicao")
public class DistribuicaoController {

    private RealizarDistribuicaoUsecase realizarDistribuicaoUsecase;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<RealizarDistribuicaoOutput> realizarDistribuicao(
            @RequestBody @Valid RealizarDistribuicaoInput realizarDistribuicaoInput
    ) {
        RealizarDistribuicaoOutput distribuicaoOutput = realizarDistribuicaoUsecase.realizarDistribuicao(realizarDistribuicaoInput);
        return new ResponseEntity<>(distribuicaoOutput, HttpStatus.OK);
    }
}
