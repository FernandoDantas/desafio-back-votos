package br.com.fernandojr.desafiobackvotos.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class SessaoEncerradaException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SessaoEncerradaException(String mensagem) {
        super(mensagem);
    }
}
