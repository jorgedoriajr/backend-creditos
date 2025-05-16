package br.com.infuse.backendcreditos.controller;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.service.CreditoProducerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/producer/credito")
public class CreditoProducerController {

    private final CreditoProducerService producerService;

    public CreditoProducerController(CreditoProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping
    public ResponseEntity<Void> sendCredito(@RequestBody CreditoDTO credito) {
        producerService.sendMessage(credito);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}