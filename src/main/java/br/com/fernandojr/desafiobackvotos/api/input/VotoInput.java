package br.com.fernandojr.desafiobackvotos.api.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.br.CPF;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "VotoInput")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VotoInput {

    @ApiModelProperty(value = "Código da pauta da votação aberta")
    @NotNull(message = "idPauta deve ser preenchido")
    private Long idPauta;
    
    @ApiModelProperty(value = "Voto")
    @NotNull(message = "Voto deve ser preenchido")
    private String voto;

    @ApiModelProperty(value = "CPF valido do associado")
    @CPF(message = "Não é um CPF valido")
    @NotBlank(message = "cpf do associado deve ser preenchido")
    private String cpfAssociado;
}
