package br.com.fernandojr.desafiobackvotos.api.input;

import java.time.LocalDateTime;

import br.com.fernandojr.desafiobackvotos.domain.model.SessaoVotacao;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "SessaoVotacaoInput")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessaoVotacaoInput {

    @ApiModelProperty(value = "Código da sessão de votação aberta")
    private Long id;

    @ApiModelProperty(value = "Data Hora de início da sessão de votação aberta")
    private LocalDateTime dataHoraInicio;

    @ApiModelProperty(value = "Data Hora de fim sessão de votação aberta")
    private LocalDateTime dataHoraFim;

    @ApiModelProperty(value = "Status da sessão de votação aberta")
    private Boolean ativa;
    
    @ApiModelProperty(value = "Código da pauta")
    private Long idPauta;


    public static SessaoVotacao toEntity(SessaoVotacaoInput dto) {
        return SessaoVotacao.builder()
                .id(dto.getId())
                .dataHoraInicio(dto.getDataHoraInicio())
                .dataHoraFim(dto.getDataHoraFim())
                .ativa(dto.getAtiva())
                .idPauta(dto.getIdPauta())
                .build();
    }

    public static SessaoVotacaoInput toDTO(SessaoVotacao sessaoVotacao) {
        return SessaoVotacaoInput.builder()
                .id(sessaoVotacao.getId())
                .dataHoraInicio(sessaoVotacao.getDataHoraInicio())
                .dataHoraFim(sessaoVotacao.getDataHoraFim())
                .ativa(sessaoVotacao.getAtiva())
                .idPauta(sessaoVotacao.getIdPauta())
                .build();
    }
}
