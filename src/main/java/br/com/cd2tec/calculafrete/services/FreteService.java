package br.com.cd2tec.calculafrete.services;

import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteOutPutDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteInputDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FretePersistDto;
import br.com.cd2tec.calculafrete.models.Frete;
import br.com.cd2tec.calculafrete.repositories.FreteRepository;
import br.com.cd2tec.calculafrete.utils.Funcao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class FreteService {

    private final FreteRepository freteRepository;

    private final ModelMapper modelMapper;

    private final Funcao funcao;

    @Autowired
    public FreteService(
            FreteRepository freteRepository,
            ModelMapper modelMapper,
            Funcao funcao) {
        this.freteRepository = freteRepository;
        this.modelMapper = modelMapper;
        this.funcao = funcao;
    }

    public FreteOutPutDto consultFrete(FreteInputDto freteInputDto){
       var freteSalvar =  modelMapper.map(funcao.consultarFrete(freteInputDto), Frete.class);

       return modelMapper.map(this.freteRepository.save(freteSalvar), FreteOutPutDto.class);
    }

    public Page<FretePersistDto> getAll(Pageable pageable){
        return this.freteRepository.findAll(pageable)
                .map( frete -> modelMapper.map(frete, FretePersistDto.class));
    }


}
