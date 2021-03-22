package br.com.fernandojr.desafiobackvotos.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import br.com.fernandojr.desafiobackvotos.domain.model.SessaoVotacao;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
	
	@Query("select s from SessaoVotacao s where s.ativa=:ativo and s.dataHoraFim < :dataAtual and idPauta = :idPauta")
    List<SessaoVotacao> buscarTodasSessoesEmAndamento(Boolean ativo, LocalDateTime dataAtual, Long idPauta);

    boolean existsByIdAndAtiva(Long id, Boolean ativa);

    boolean existsById(Long id);
    
    Optional<SessaoVotacao> findByIdAndAtiva(Long id, Boolean ativa);
    
    Optional<SessaoVotacao> findByIdPautaAndAtiva(Long idPauta, Boolean ativa);
    
    @Query("select s from SessaoVotacao s where s.idPauta=:idPauta and s.dataHoraFim >= :dataAtual")
    Optional<SessaoVotacao> buscarPorPautaEdataFinal(Long idPauta, LocalDateTime dataAtual);
    

   
}
