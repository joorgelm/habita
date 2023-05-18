package br.com.jorge.habita.adapter.controller.distribuicao;

import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoOutput;
import br.com.jorge.habita.application.usecase.distribuicao.RealizarDistribuicaoUsecase;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping(value = "distribuicao")
public class DistribuicaoController {

    private RealizarDistribuicaoUsecase realizarDistribuicaoUsecase;

    // todo: receber quantidade de casas a serem sorteadas
    //todo: receber via queryparam e valor default 1
    @GetMapping
    public ResponseEntity<RealizarDistribuicaoOutput> realizarDistribuicao() {
        RealizarDistribuicaoOutput DistribuicaoOutput = realizarDistribuicaoUsecase.sortear();

        return new ResponseEntity<>(DistribuicaoOutput, HttpStatus.OK);
    }

}
