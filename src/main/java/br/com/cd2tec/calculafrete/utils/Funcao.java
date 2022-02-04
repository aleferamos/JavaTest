package br.com.cd2tec.calculafrete.utils;

import br.com.cd2tec.calculafrete.controllers.dtos.Cep.ConsultaCepDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteParamDto;
import br.com.cd2tec.calculafrete.services.ViaCepFeignClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Service
public class Funcao {

    private ModelMapper modelMapper;
    private final ViaCepFeignClient viaCepFeignClient;

    @Autowired
    public Funcao(
            ModelMapper modelMapper,
            ViaCepFeignClient viaCepFeignClient) {
        this.modelMapper = modelMapper;
        this.viaCepFeignClient = viaCepFeignClient;
    }

    private LocalDate verificarData(int dias){
        var today = LocalDate.now();

        Calendar cal = Calendar.getInstance();

        Date date = modelMapper.map(LocalDate.now(), Date.class);

        cal.setTime(date);
        cal.add(Calendar.DATE, dias);
        date = cal.getTime();

        return date.toInstant().atZone( ZoneId.systemDefault()).toLocalDate();
    }

    private ConsultaCepDto consultarCep(String cep){


        return this.viaCepFeignClient.buscaCep(cep.replace(" ","")).getBody();
    }

    public FreteDto consulta(FreteParamDto freteParamDto){

        var cepOrigem =  consultarCep(freteParamDto.getCepOrigem());

        var cepDestino =  consultarCep(freteParamDto.getCepDestino());


        return verificacao(cepOrigem, cepDestino, freteParamDto);

    }

    private FreteDto verificacao(ConsultaCepDto cepOrigem, ConsultaCepDto cepDestino, FreteParamDto freteParamDto){

        var freteSave = modelMapper.map(freteParamDto, FreteDto.class);

        freteSave.setVlTotalFrete((freteSave.getPeso()));

        if(cepOrigem.getDdd().equals(cepDestino.getDdd()) && cepOrigem.getUf().equals(cepDestino.getUf())){
            var valorFrete = freteSave.getVlTotalFrete() - (freteSave.getVlTotalFrete() * 0.50);
            freteSave.setVlTotalFrete(valorFrete);

            freteSave.setDataPrevistaEntrega(verificarData(1));
            freteSave.setDataConsulta(LocalDate.now());
            return freteSave;
        }
//
        if(cepOrigem.getDdd().equals(cepDestino.getDdd())){
            var valorFrete = freteSave.getVlTotalFrete() - (freteSave.getVlTotalFrete() * 0.50);
            freteSave.setVlTotalFrete(valorFrete);

            freteSave.setDataPrevistaEntrega(verificarData(1));
            freteSave.setDataConsulta(LocalDate.now());
            return freteSave;
        }

        if(cepOrigem.getUf().equals(cepDestino.getUf())){
            var valorFrete = freteSave.getVlTotalFrete() - (freteSave.getVlTotalFrete() * 0.75);
            freteSave.setVlTotalFrete(valorFrete);

            freteSave.setDataPrevistaEntrega(verificarData(3));
            freteSave.setDataConsulta(LocalDate.now());
            return freteSave;
        } else {
            freteSave.setVlTotalFrete(freteSave.getPeso());
            freteSave.setDataPrevistaEntrega(verificarData(10));
            freteSave.setDataConsulta(LocalDate.now());
            return freteSave;
        }
    }
}
