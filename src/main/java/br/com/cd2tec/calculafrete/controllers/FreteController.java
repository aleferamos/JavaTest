package br.com.cd2tec.calculafrete.controllers;

import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteOutPutDto;
import br.com.cd2tec.calculafrete.controllers.dtos.frete.FreteInputDto;
import br.com.cd2tec.calculafrete.services.FreteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("frete")
public class FreteController {

    private final FreteService freteService;

    @Autowired
    public FreteController(FreteService freteService) {
        this.freteService = freteService;
    }

    @PostMapping
   public ResponseEntity<FreteOutPutDto> consultarFrete(@RequestBody FreteInputDto freteInputDto){
        return ResponseEntity.ok(this.freteService.consultFrete(freteInputDto));
   }
}
