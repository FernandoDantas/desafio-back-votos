package br.com.fernandojr.desafiobackvotos.domain.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fernandojr.desafiobackvotos.api.input.SessaoVotacaoAbrirInput;
import br.com.fernandojr.desafiobackvotos.api.input.SessaoVotacaoInput;
import br.com.fernandojr.desafiobackvotos.domain.exception.NotFoundException;
import br.com.fernandojr.desafiobackvotos.domain.model.SessaoVotacao;
import br.com.fernandojr.desafiobackvotos.domain.repository.SessaoVotacaoRepository;


@Service
public class SessaoVotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoService.class);
    private static final Integer TEMPO_DEFAULT = 1;

    private final SessaoVotacaoRepository repository;
    private final PautaService pautaService;

    @Autowired
    public SessaoVotacaoService(SessaoVotacaoRepository repository, PautaService pautaService) {
        this.repository = repository;
        this.pautaService = pautaService;
    }
   
    @Transactional
    public SessaoVotacaoInput abrirSessaoVotacao(SessaoVotacaoAbrirInput sessaoVotacaoAbrirInput) {
        LOGGER.debug("Abrindo a sessao de votacao para a pauta {}", sessaoVotacaoAbrirInput.getIdPauta());

        isValidaAbrirSessao(sessaoVotacaoAbrirInput);

        SessaoVotacaoInput dto = new SessaoVotacaoInput(
        		null,
        		LocalDateTime.now(),
        		calcularTempo(sessaoVotacaoAbrirInput.getTempo()),
        		Boolean.TRUE,
        		sessaoVotacaoAbrirInput.getIdPauta());
        
        encerraSessaoVotacao(sessaoVotacaoAbrirInput);
        
        return salvar(dto);
    }    

    
    @Transactional(readOnly = true)
    public boolean isValidaAbrirSessao(SessaoVotacaoAbrirInput sessaoVotacaoAbrirInput) {
        if (pautaService.isPautaValida(sessaoVotacaoAbrirInput.getIdPauta())) {
        	Optional<SessaoVotacao> sessao = repository.buscarPorPautaEdataFinal(sessaoVotacaoAbrirInput.getIdPauta(), LocalDateTime.now());
        	if(sessao.isPresent()) {
        		throw new NotFoundException("Já existe uma sessão aberta para essa pauta " + sessaoVotacaoAbrirInput.getIdPauta());
        	}
            return Boolean.TRUE;
        } else {
            throw new NotFoundException("Pauta não localizada idPauta" + sessaoVotacaoAbrirInput.getIdPauta());
        }
    }    
   
    @Transactional
    public void encerraSessaoVotacao(SessaoVotacaoAbrirInput sessaoDTO) {
    	List<SessaoVotacao> sessoes = repository.buscarTodasSessoesEmAndamento(true, LocalDateTime.now(), sessaoDTO.getIdPauta());
    	for(SessaoVotacao sessao : sessoes) {
    		sessao.setAtiva(false);
    		repository.save(sessao);
    	}
    }
   
    @Transactional(readOnly = true)
    public boolean isSessaoVotacaoAberta(Long id) {
    	Optional<SessaoVotacao> sessaoVotacao = repository.findByIdAndAtiva(id, true);
    	if(sessaoVotacao.isPresent()) {
    		if(sessaoVotacao.get().getDataHoraFim().isAfter(LocalDateTime.now()) || 
    		   sessaoVotacao.get().getDataHoraFim().equals(LocalDateTime.now())) {
    			
    			return true;   			
    		}  		
    	}
    	return false;
    }

   
    @Transactional(readOnly = true)
    public boolean isSessaoVotacaoExiste(Long id) {
        if (repository.existsById(id)) {
            return Boolean.TRUE;
        } else {
            LOGGER.error("Sessao de votacao nao localizada para o id {}", id);
            throw new NotFoundException("Sessão de votação não localizada para o id " + id);
        }
    }

   
    private LocalDateTime calcularTempo(Integer tempo) {
        if (tempo != null && tempo != 0) {
        	System.out.println(LocalDateTime.now().plusMinutes(tempo));
            return LocalDateTime.now().plusMinutes(tempo);
        } else {
            return LocalDateTime.now().plusMinutes(TEMPO_DEFAULT);
        }
    }

    
    @Transactional
    public SessaoVotacaoInput salvar(SessaoVotacaoInput dto) {
        LOGGER.debug("Salvando a sessao de votacao");
        if (Optional.ofNullable(dto).isPresent()) {
            return SessaoVotacaoInput.toDTO(repository.save(SessaoVotacaoInput.toEntity(dto)));
        }
        return null;
    }
    
    
    
    @Transactional(readOnly = true)
    public Optional<SessaoVotacao> buscarSessaoPorPauta(Long idPauta) {
    	return repository.findByIdPautaAndAtiva(idPauta, true);
    }
}
