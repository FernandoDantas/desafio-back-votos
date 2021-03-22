package br.com.fernandojr.desafiobackvotos.api.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "ResultadoInput")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultadoInput {

    @ApiModelProperty(value = "Objeto PautaDTO com os dados do que foi votado")
    private PautaInput pauta;
    
    @ApiModelProperty(value = "Objeto VotacaoDTO com dados do resultado da votação")
    private VotacaoInput votacao;
}
