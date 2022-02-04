package br.com.cd2tec.calculafrete.services;

import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteOutPutDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteInputDto;
import br.com.cd2tec.calculafrete.models.Frete;
import br.com.cd2tec.calculafrete.repositories.FreteReposotiry;
import br.com.cd2tec.calculafrete.utils.Funcao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public FreteOutPutDto consultFrete(FreteInputDto freteInputDto){

       var freteSalvar =  modelMapper.map(funcao.consulta(freteInputDto), Frete.class);

       return modelMapper.map(this.freteReposotiry.save(freteSalvar), FreteOutPutDto.class);
    }



}
