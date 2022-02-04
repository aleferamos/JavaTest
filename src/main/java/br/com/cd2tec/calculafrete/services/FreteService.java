package br.com.cd2tec.calculafrete.services;

import br.com.cd2tec.calculafrete.controllers.dtos.Cep.ConsultaCepDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteOutPutDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteParamDto;
import br.com.cd2tec.calculafrete.models.Frete;
import br.com.cd2tec.calculafrete.repositories.FreteReposotiry;
import br.com.cd2tec.calculafrete.utils.Funcao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class FreteService {

    private final FreteReposotiry freteReposotiry;

    private final ModelMapper modelMapper;

    private final ViaCepFeignClient viaCepFeignClient;

    private final Funcao funcao;

    @Autowired
    public FreteService(
            FreteReposotiry freteReposotiry,
            ModelMapper modelMapper,
            ViaCepFeignClient viaCepFeignClient,
            Funcao funcao) {
        this.freteReposotiry = freteReposotiry;
        this.modelMapper = modelMapper;
        this.viaCepFeignClient = viaCepFeignClient;
        this.funcao = funcao;
    }

    public FreteOutPutDto consultFrete(FreteParamDto freteParamDto){

       var freteSalvar =  modelMapper.map(funcao.consulta(freteParamDto), Frete.class);

       return modelMapper.map(this.freteReposotiry.save(freteSalvar), FreteOutPutDto.class);
    }



}
