package br.com.fernandojr.desafiobackvotos.api.input;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "SessaoVotacaoAbrirInput")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessaoVotacaoAbrirInput {

    @ApiModelProperty(value = "Código da Pauta que se quer abrir sessão para votação")
    @NotNull(message = "Código deve ser preenchido")
    private Long idPauta;

    @ApiModelProperty(value = "Tempo em MINUTOS que a sessão de votação deverá ficar disponível")
    private Integer tempo;
}
