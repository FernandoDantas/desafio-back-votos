package br.com.fernandojr.desafiobackvotos.api.input;

import javax.validation.constraints.NotBlank;

import br.com.fernandojr.desafiobackvotos.domain.model.Pauta;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "PautaInput")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PautaInput {
	
	@ApiModelProperty(value = "Código da Pauta", required = true)
    private Long id;

    @ApiModelProperty(value = "Descrição referente ao que será votado")
    @NotBlank(message = "Descrição deve ser preenchido")
    private String descricao;

    public static Pauta toEntity(PautaInput dto) {
        return Pauta.builder()
                .id(dto.getId())
                .descricao(dto.getDescricao())
                .build();
    }

    public static PautaInput toDTO(Pauta pauta) {
        return PautaInput.builder()
                .id(pauta.getId())
                .descricao(pauta.getDescricao())
                .build();
    }

}
