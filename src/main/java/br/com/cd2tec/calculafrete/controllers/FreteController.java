package br.com.cd2tec.calculafrete.controllers;

import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteOutPutDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteInputDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FretePersistDto;
import br.com.cd2tec.calculafrete.services.FreteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.net.URI;

@RestController
@RequestMapping("frete")
@Api(tags = {" End Points Frete"})
public class FreteController {

    private final FreteService freteService;

    @Autowired
    public FreteController(FreteService freteService) {
        this.freteService = freteService;
    }

    @PostMapping
    @Transactional
    @ApiOperation("Endpoint responsável por consultar e calcular o frete.")
    public ResponseEntity<FreteOutPutDto> consultarFrete(@RequestBody FreteInputDto freteInputDto){

        URI uri = UriComponentsBuilder.fromPath("frete").buildAndExpand(freteInputDto).toUri();

        return ResponseEntity.created(uri).body(this.freteService.consultFrete(freteInputDto));
    }

    @GetMapping
    @ApiOperation("Endpoint responsável por listar todos os fretes consultados e calculados que estão no banco")
    public ResponseEntity<Page<FretePersistDto>> listarTodosFretes(Pageable pageable){
        return ResponseEntity.ok(freteService.getAll(pageable));
    }
}
