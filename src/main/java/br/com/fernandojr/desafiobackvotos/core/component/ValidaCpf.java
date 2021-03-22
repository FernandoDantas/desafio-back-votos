package br.com.fernandojr.desafiobackvotos.core.component;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fernandojr.desafiobackvotos.domain.exception.NotFoundException;

@Component
public class ValidaCpf {
	
	private static final String ABLE_TO_VOTE = "ABLE_TO_VOTE";

    /**
     * Integração com um sistema externo que verifica, a partir do CPF do associado, se ele pode votar
     *GET https://user-info.herokuapp.com/users/{cpf}
     *Caso o CPF seja inválido, a API retornará o HTTP Status 404 (Not found). Você pode usar geradores de CPF para gerar CPFs válidos;
     *Caso o CPF seja válido, a API retornará se o usuário pode (ABLE_TO_VOTE) ou não pode (UNABLE_TO_VOTE) executar a operação Exemplos de retorno do serviço     
     * @param cpf - @{@link br.com.api.votacao.entity.Associado} CPF valido
     * @return - boolean
     */
    public boolean isVerificaAssociadoHabilitadoVotacao(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String fooResourceUrl = "https://user-info.herokuapp.com/users/{cpf}";
            ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl, String.class, cpf);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode status = root.path("status");

            if (ABLE_TO_VOTE.equals(status.textValue())) {
                return Boolean.TRUE;
            }

            return Boolean.FALSE;

        } catch (HttpClientErrorException | IOException ex) {
            throw new NotFoundException("Não foi possivel localizar o CPF do associado");
        }
    }

}
