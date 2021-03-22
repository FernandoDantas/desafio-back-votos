package br.com.fernandojr.desafiobackvotos.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.fernandojr.desafiobackvotos.api.input.AssociadoInput;
import br.com.fernandojr.desafiobackvotos.core.component.ValidaCpf;
import br.com.fernandojr.desafiobackvotos.domain.repository.AssociadoRepository;

@Service
public class AssociadoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssociadoService.class);
    private final AssociadoRepository repository;
    private ValidaCpf validaCpf;

    @Autowired
    public AssociadoService(AssociadoRepository repository, ValidaCpf validaCpf) {
        this.repository = repository;
        this.validaCpf = validaCpf;
    }

    /**
     * Realiza a validacao se o associado ja votou na pauta informada pelo seu CPF.
     * Se nao existir um registro na base, entao e considerado como valido para seu voto ser computado
     *
     * @param cpfAssociado @{@link br.com.api.votacao.entity.Associado} CPF Valido
     * @param idPauta     @{@link br.com.api.votacao.entity.Pauta} ID
     * @return - boolean
     */
    @Transactional(readOnly = true)
    public boolean isValidaVotacaoAssociado(String cpfAssociado, Long idPauta) {
        LOGGER.debug("Validando participacao do associado na votacao da pauta  id = {}", idPauta);
        if (repository.existsByCpfAssociadoAndIdPauta(cpfAssociado, idPauta)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
    
    @Transactional
    public void salvarAssociado(AssociadoInput dto) {
        LOGGER.debug("Registrando participação do associado na votação idAssociado = {}, idPauta = {}", dto.getCpfAssociado(), dto.getIdPauta());
        repository.save(AssociadoInput.toEntity(dto));
    }

    /**
     * faz a chamada para metodo que realiza a consulta em API externa
     * para validar o cpf informado
     *
     * @param cpf - @{@link AssociadoInput} CPF valido
     * @return - boolean
     */
    public boolean isAssociadoPodeVotar(String cpf) {
        return validaCpf.isVerificaAssociadoHabilitadoVotacao(cpf);
    }
}
