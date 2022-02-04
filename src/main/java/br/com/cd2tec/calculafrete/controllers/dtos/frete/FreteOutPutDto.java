package br.com.cd2tec.calculafrete.controllers.dtos.frete;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FreteOutPutDto {

    private Double vlTotalFrete;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate dataPrevistaEntrega;

    private String cepOrigem;

    private String cepDestino;
}
