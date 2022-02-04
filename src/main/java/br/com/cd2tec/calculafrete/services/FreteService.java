package br.com.cd2tec.calculafrete.services;

import br.com.cd2tec.calculafrete.controllers.dtos.Cep.ConsultaCepDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteParamDto;
import br.com.cd2tec.calculafrete.models.Frete;
import br.com.cd2tec.calculafrete.repositories.FreteReposotiry;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Service
public class FreteService {

    private final FreteReposotiry freteReposotiry;

    private final ModelMapper modelMapper;

    private final ViaCepFeignClient viaCepFeignClient;


    @Autowired
    public FreteService(
            FreteReposotiry freteReposotiry,
            ModelMapper modelMapper,
            ViaCepFeignClient viaCepFeignClient) {
        this.freteReposotiry = freteReposotiry;
        this.modelMapper = modelMapper;
        this.viaCepFeignClient = viaCepFeignClient;
    }

    public Long consultFrete(FreteParamDto freteParamDto){
        Frete frete = modelMapper.map(freteParamDto, Frete.class);
        frete.setDataConsulta(LocalDate.now());




        System.out.println(this.verificacao(freteParamDto).getDataPrevistaEntrega() + " " + this.verificacao(freteParamDto).getVlTotalFrete());
//        this.freteReposotiry.save(frete);
        return frete.getId();
    }

    private FreteDto verificacao(FreteParamDto freteParamDto){

        var cepOrigem =  this.consultarCep(freteParamDto.getCepOrigem().replace(" ",""));

        var cepDestino =  this.consultarCep(freteParamDto.getCepDestino().replace(" ",""));

        var freteSave = modelMapper.map(freteParamDto, FreteDto.class);

        freteSave.setVlTotalFrete((freteSave.getPeso()));

        if(cepOrigem.getDdd().equals(cepDestino.getDdd()) && cepOrigem.getUf().equals(cepDestino.getUf())){
            var valorFrete = freteSave.getVlTotalFrete() - (freteSave.getVlTotalFrete() * 0.50);
            freteSave.setVlTotalFrete(valorFrete);

            freteSave.setDataPrevistaEntrega(verificarData(1));
            return freteSave;
        }

        if(cepOrigem.getDdd().equals(cepDestino.getDdd())){
            var valorFrete = freteSave.getVlTotalFrete() - (freteSave.getVlTotalFrete() * 0.50);
            freteSave.setVlTotalFrete(valorFrete);

            freteSave.setDataPrevistaEntrega(verificarData(1));
            return freteSave;
        }

        if(cepOrigem.getUf().equals(cepDestino.getUf())){
            var valorFrete = freteSave.getVlTotalFrete() - (freteSave.getVlTotalFrete() * 0.75);
            freteSave.setVlTotalFrete(valorFrete);

            freteSave.setDataPrevistaEntrega(verificarData(3));
            return freteSave;
        } else {
            freteSave.setVlTotalFrete(freteSave.getPeso());
            freteSave.setDataPrevistaEntrega(verificarData(10));

            return freteSave;
        }
    }

    private ConsultaCepDto consultarCep(String cep){

        ConsultaCepDto cepReturn = this.viaCepFeignClient.buscaCep(cep).getBody();

        return cepReturn;
    }

    private LocalDate verificarData(int dias){
        var today = LocalDate.now();

        Calendar cal = Calendar.getInstance();

        Date date = modelMapper.map(LocalDate.now(), Date.class);

        cal.setTime(date);
        cal.add(Calendar.DATE, dias);
        date = cal.getTime();

        LocalDate dataConvert = date.toInstant().atZone( ZoneId.systemDefault()).toLocalDate();

        return dataConvert;
    }
}
