package br.com.cd2tec.calculafrete.controllers.dtos.frete;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FretePersistDto {

    private Double peso;

    private String cepOrigem;

    private String cepDestino;

    private String nomeDestinatario;

    private Double vlTotalFrete;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataPrevistaEntrega;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataConsulta;

}
