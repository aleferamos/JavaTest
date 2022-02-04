package br.com.cd2tec.calculafrete.controllers.dtos.frete;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FreteDto {

    private Long id;

    private Double peso;

    private String cepOrigem;

    private String cepDestino;

    private String nomeDestinatario;

    private Double vlTotalFrete;

    private LocalDate dataPrevistaEntrega;

    private LocalDate dataConsulta;
}
