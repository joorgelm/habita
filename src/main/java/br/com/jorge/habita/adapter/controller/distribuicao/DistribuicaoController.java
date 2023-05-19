package br.com.jorge.habita.adapter.controller.distribuicao;

import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoOutput;
import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoUsecase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping(value = "distribuicao")
public class DistribuicaoController {

    private RealizarDistribuicaoUsecase realizarDistribuicaoUsecase;
    @GetMapping
    public ResponseEntity<RealizarDistribuicaoOutput> realizarDistribuicao(@RequestParam(name = "qtd", defaultValue = "1") Integer quantidadeCasas) {
        RealizarDistribuicaoOutput distribuicaoOutput = realizarDistribuicaoUsecase.sortear(quantidadeCasas);

        return new ResponseEntity<>(distribuicaoOutput, HttpStatus.OK);
    }

}
