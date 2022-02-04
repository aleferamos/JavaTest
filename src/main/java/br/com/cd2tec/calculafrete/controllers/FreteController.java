package br.com.cd2tec.calculafrete.controllers;

import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteOutPutDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteInputDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FretePersistDto;
import br.com.cd2tec.calculafrete.services.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping("frete")
public class FreteController {

    private final FreteService freteService;

    @Autowired
    public FreteController(FreteService freteService) {
        this.freteService = freteService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<FreteOutPutDto> consultarFrete(@RequestBody FreteInputDto freteInputDto){
        return ResponseEntity.ok(this.freteService.consultFrete(freteInputDto));
    }

    @GetMapping
    public ResponseEntity<Page<FretePersistDto>> listarTodosFretes(Pageable pageable){
        return ResponseEntity.ok(freteService.getAll(pageable));
    }
}
