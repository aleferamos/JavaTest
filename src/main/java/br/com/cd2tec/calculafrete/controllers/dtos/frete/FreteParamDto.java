package br.com.cd2tec.calculafrete.controllers.dtos.frete;

import lombok.Getter;

@Getter
public class FreteParamDto {

    private final Double peso;

    private final String cepOrigem;

    private final String cepDestino;

    private final String nomeDestinatario;

    public FreteParamDto(Double peso, String cepOrigem, String cepDestino, String nomeDestinatario) {
        this.peso = peso;
        this.cepOrigem = cepOrigem;
        this.cepDestino = cepDestino;
        this.nomeDestinatario = nomeDestinatario;
    }
}
