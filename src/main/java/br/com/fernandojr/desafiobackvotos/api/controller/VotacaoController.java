package br.com.fernandojr.desafiobackvotos.api.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fernandojr.desafiobackvotos.api.input.ResultadoInput;
import br.com.fernandojr.desafiobackvotos.api.input.VotoInput;
import br.com.fernandojr.desafiobackvotos.domain.service.VotacaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/v1/votacao")
@Api(value = "Votacao", tags = "Votacao")
public class VotacaoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoController.class);

    private final VotacaoService cadastroVotoService;

    @Autowired
    public VotacaoController(VotacaoService cadastroVotoService) {
        this.cadastroVotoService = cadastroVotoService;
    }

    @ApiOperation(value = "Votar em determinada pauta, enquanto a sessão de votação estiver aberta")
    @PostMapping(value = "/votar")
    public ResponseEntity<String> votar(@Valid @RequestBody VotoInput dto) {
        LOGGER.debug("Associado votando CPF = {}", dto.getCpfAssociado());
        String mensagem = cadastroVotoService.votar(dto);
        LOGGER.debug("Voto associado finalizado associado = {}", dto.getCpfAssociado());
        return ResponseEntity.status(HttpStatus.CREATED).body(mensagem);
    }

    @ApiOperation(value = "Resultado da votacao, somente após finalização da sessão de votação")
    @GetMapping(value = "/resultado/{idPauta}/{idSessaoVotacao}")
    public ResponseEntity<ResultadoInput> resultadoVotacao(@PathVariable("idPauta") Long idPauta, @PathVariable("idSessaoVotacao") Long idSessaoVotacao) {
        LOGGER.debug("Buscando resultado da votacao idPauta = {} , idSessaoVotacao = {} ", idPauta, idSessaoVotacao);
        ResultadoInput dto = cadastroVotoService.buscarDadosResultadoVotacao(idPauta, idSessaoVotacao);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}

