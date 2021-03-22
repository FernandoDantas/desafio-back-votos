package br.com.fernandojr.desafiobackvotos.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fernandojr.desafiobackvotos.domain.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long> {

    Boolean existsByCpfAssociadoAndIdPauta(String cpfAssociado, Long idPauta);
}
