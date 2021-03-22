package br.com.fernandojr.desafiobackvotos.domain.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fernandojr.desafiobackvotos.api.input.PautaInput;
import br.com.fernandojr.desafiobackvotos.domain.exception.NotFoundException;
import br.com.fernandojr.desafiobackvotos.domain.model.Pauta;
import br.com.fernandojr.desafiobackvotos.domain.repository.PautaRepository;

@Service
public class PautaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PautaService.class);

    private final PautaRepository pautaRepository;

    @Autowired
    public PautaService(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    @Transactional
    public PautaInput salvar(PautaInput dto) {
        return PautaInput.toDTO(pautaRepository.save(PautaInput.toEntity(dto)));
    }

    
    @Transactional(readOnly = true)
    public PautaInput buscarPautaPeloId(Long id) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(id);

        if (!pautaOptional.isPresent()) {
            LOGGER.error("Pauta não localizada para id {}", id);
            throw new NotFoundException("Pauta não localizada para o id " + id);
        }

        return PautaInput.toDTO(pautaOptional.get());
    }
   
    @Transactional(readOnly = true)
    public boolean isPautaValida(Long long1) {
        if (pautaRepository.existsById(long1)) {
            return Boolean.TRUE;
        } else {
        	return Boolean.FALSE;
        }
    }
}
