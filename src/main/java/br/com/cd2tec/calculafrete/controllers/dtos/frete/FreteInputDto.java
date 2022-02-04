package br.com.cd2tec.calculafrete.controllers.dtos.frete;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;

@Getter
public class FreteInputDto {

    private final Double peso;

    @NotEmpty(message = "Campo cep de origem não pode ser vazio!")
    private final String cepOrigem;

    @NotEmpty(message = "Campo cep de destino não pode ser vazio!")
    private final String cepDestino;

    private final String nomeDestinatario;

    public FreteInputDto(Double peso, String cepOrigem, String cepDestino, String nomeDestinatario) {
        this.peso = peso;
        this.cepOrigem = cepOrigem;
        this.cepDestino = cepDestino;
        this.nomeDestinatario = nomeDestinatario;
    }
}
