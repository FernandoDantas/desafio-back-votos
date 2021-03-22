package br.com.fernandojr.desafiobackvotos.api.input;

import br.com.fernandojr.desafiobackvotos.domain.model.Votacao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "VotacaoInput")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoInput {

    @ApiModelProperty(value = "ID da votação aberta")
    private Long id;

    @ApiModelProperty(value = "ID da pauta da votação aberta")
    private Long idPauta;

    @ApiModelProperty(value = "ID da sessão de votação aberta")
    private Long idSessaoVotacao;
    
    @ApiModelProperty(value = "Voto")
    private String voto;

    @ApiModelProperty(value = "Quantidade de votos positivos")
    private Integer quantidadeVotosSim;

    @ApiModelProperty(value = "Quantidade de votos negativos")
    private Integer quantidadeVotosNao;

    public static Votacao toEntity(VotacaoInput dto) {
        return Votacao.builder()
                .id(dto.getId())
                .idPauta(dto.getIdPauta())
                .idSessaoVotacao(dto.getIdSessaoVotacao())
                .voto(dto.getVoto())
                .build();
    }
}

