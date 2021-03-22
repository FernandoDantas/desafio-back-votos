package br.com.fernandojr.desafiobackvotos.domain.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fernandojr.desafiobackvotos.api.input.AssociadoInput;
import br.com.fernandojr.desafiobackvotos.api.input.PautaInput;
import br.com.fernandojr.desafiobackvotos.api.input.ResultadoInput;
import br.com.fernandojr.desafiobackvotos.api.input.VotacaoInput;
import br.com.fernandojr.desafiobackvotos.api.input.VotoInput;
import br.com.fernandojr.desafiobackvotos.domain.exception.NotFoundException;
import br.com.fernandojr.desafiobackvotos.domain.exception.SessaoEncerradaException;
import br.com.fernandojr.desafiobackvotos.domain.exception.VotoInvalidoException;
import br.com.fernandojr.desafiobackvotos.domain.model.SessaoVotacao;
import br.com.fernandojr.desafiobackvotos.domain.repository.VotacaoRepository;

@Service
public class VotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotacaoService.class);

    private final VotacaoRepository repository;
    private final PautaService pautaService;
    private final SessaoVotacaoService sessaoVotacaoService;
    private final AssociadoService associadoService;

    @Autowired
    public VotacaoService(VotacaoRepository repository, PautaService pautaService, SessaoVotacaoService sessaoVotacaoService, AssociadoService associadoService) {
        this.repository = repository;
        this.pautaService = pautaService;
        this.sessaoVotacaoService = sessaoVotacaoService;
        this.associadoService = associadoService;
    }

       @Transactional(readOnly = true)
    public boolean isValidaVoto(String cpfAssociado,  Long idPauta, Long idSessaoVotacao, String voto) {
        LOGGER.debug("Validando os dados para voto idPauta = {}, idAssociado = {}", idPauta, cpfAssociado);

        sessaoVotacaoService.buscarSessaoPorPauta(idPauta);
        
        if (!pautaService.isPautaValida(idPauta)) {

            LOGGER.error("Pauta nao localizada para votacao idPauta {}", idPauta);
            throw new NotFoundException("Pauta nao localizada para votacao id " + idPauta);

        } else if (!sessaoVotacaoService.isSessaoVotacaoAberta(idSessaoVotacao)) {

            LOGGER.error("Tentativa de voto para sessao encerrada idSessaoVotacao {}", idSessaoVotacao);
            throw new SessaoEncerradaException("Sessão de votação já encerrada");

        } else if (!associadoService.isAssociadoPodeVotar(cpfAssociado)) {

            LOGGER.error("Associado nao autorizado a votar {}", cpfAssociado);
            throw new VotoInvalidoException("Associado nao autorizado a votar");

        } else if (!associadoService.isValidaVotacaoAssociado(cpfAssociado, idPauta)) {

            LOGGER.error("Associado tentou votar mais de 1 vez idAssociado {}", cpfAssociado);
            throw new VotoInvalidoException("Não é possível votar mais de 1 vez na mesma pauta");
        
        } else if (!voto.equalsIgnoreCase("sim") && !voto.equalsIgnoreCase("não")) {
        	
        	LOGGER.error("So é possível votar sim ou não {}", voto);
            throw new VotoInvalidoException("So é possível votar sim ou não");
        }

        return Boolean.TRUE;
    }
   
    @Transactional
    public String votar(VotoInput dto) {
    	 Optional<SessaoVotacao> sessao = sessaoVotacaoService.buscarSessaoPorPauta(dto.getIdPauta());
    	 if(sessao.isPresent()) {
    		 if (isValidaVoto(dto.getCpfAssociado(), dto.getIdPauta(), sessao.get().getId(), dto.getVoto())) {
    	            LOGGER.debug("Dados validos para voto idPauta = {}, idAssociado = {}", 
    	                    dto.getIdPauta(), dto.getCpfAssociado());
    	            
    	            VotacaoInput votacaoDTO = new VotacaoInput(null,
    	                    dto.getIdPauta(),
    	                    sessao.get().getId(),
    	                    dto.getVoto().toUpperCase(),
    	                    null,
    	                    null);

    	            registrarVoto(votacaoDTO);

    	            registrarAssociadoVotou(dto);

    	            return "Voto validado";
    	        }
    		 
    	 }
   
        return "Nenhuma sessão foi aberta para essa pauta";
    }
   
    @Transactional
    public void registrarAssociadoVotou(VotoInput dto) {
        AssociadoInput associadoDTO = new AssociadoInput(null, dto.getCpfAssociado(), dto.getIdPauta());
        associadoService.salvarAssociado(associadoDTO);
    }

    @Transactional
    public void registrarVoto(VotacaoInput dto) {
        LOGGER.debug("Salvando o voto para idPauta {}", dto.getIdPauta());
        repository.save(VotacaoInput.toEntity(dto));
    }

   
    @Transactional(readOnly = true)
    public VotacaoInput buscarResultadoVotacao(Long idPauta, Long idSessaoVotacao) {
        LOGGER.debug("Contabilizando os votos para idPauta = {}, idSessaoVotacao = {}", idPauta, idSessaoVotacao);
        VotacaoInput dto = new VotacaoInput();

        dto.setIdPauta(idPauta);
        dto.setIdSessaoVotacao(idSessaoVotacao);

        dto.setQuantidadeVotosSim(repository.countVotacaoByIdPautaAndIdSessaoVotacaoAndVoto(idPauta, idSessaoVotacao, "SIM"));
        dto.setQuantidadeVotosNao(repository.countVotacaoByIdPautaAndIdSessaoVotacaoAndVoto(idPauta, idSessaoVotacao, "NÃO"));

        return dto;
    }
   
    @Transactional(readOnly = true)
    public ResultadoInput buscarDadosResultadoVotacao(Long idPauta, Long idSessaoVotacao) {    	
    	if(sessaoVotacaoService.isSessaoVotacaoExiste(idSessaoVotacao)) { 		
    		if(!sessaoVotacaoService.isSessaoVotacaoAberta(idSessaoVotacao)) {		
                LOGGER.debug("Construindo o objeto de retorno do resultado para idPauta = {}, idSessaoVotacao = {}", idPauta, idSessaoVotacao);
                PautaInput pautaInput = pautaService.buscarPautaPeloId(idPauta);
                VotacaoInput votacaoInput = buscarResultadoVotacao(idPauta, idSessaoVotacao);
                return new ResultadoInput(pautaInput, votacaoInput);		
    		}else {
    		   throw new NotFoundException("Sessão de votação ainda está aberta, não é possível obter a contagem do resultado.");
    		}
    	}
    	
    	throw new NotFoundException("Sessão de votação não encontrada.");
    }
    
}
