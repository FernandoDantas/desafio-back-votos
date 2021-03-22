package br.com.fernandojr.desafiobackvotos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fernandojr.desafiobackvotos.domain.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {
	
	boolean existsById(Long id);


}
