package br.com.cd2tec.calculafrete.services;

import br.com.cd2tec.calculafrete.controllers.dtos.Cep.ConsultaCepDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "viaCep", url = "https://viacep.com.br/ws")
public interface ViaCepFeignClient {

    @GetMapping("/{cep}/json/")
    public ResponseEntity<ConsultaCepDto> buscaCep(@PathVariable String cep);
}
