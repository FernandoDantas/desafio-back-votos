package br.com.fernandojr.desafiobackvotos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fernandojr.desafiobackvotos.domain.model.Votacao;

@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Long> {

	Integer countVotacaoByIdPautaAndIdSessaoVotacaoAndVoto(Long idPauta, Long idSessaoVotacao, String voto);
	
}
