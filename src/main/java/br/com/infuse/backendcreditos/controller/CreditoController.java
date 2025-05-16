package br.com.infuse.backendcreditos.controller;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.service.CreditoProducerService;
import br.com.infuse.backendcreditos.service.CreditoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CreditoController {

    private final CreditoService creditoService;
    private final CreditoProducerService producerService;

    public CreditoController(CreditoService creditoService, CreditoProducerService producerService) {
        this.creditoService = creditoService;
        this.producerService = producerService;
    }

    @GetMapping("/creditos/credito/{numeroCredito}")
    List<CreditoDTO> findByNumeroCredito(@PathVariable String numeroCredito) {
        List<CreditoDTO> creditos = creditoService.findByNumeroCredito(numeroCredito);
        creditos.forEach(producerService::sendMessage);
        return creditos;
    }

    @GetMapping("/creditos/{numeroNfse}")
    List<CreditoDTO> findByNumeroNfse(@PathVariable String numeroNfse) {
        List<CreditoDTO> creditos = creditoService.findByNumeroNfse(numeroNfse);
        creditos.forEach(producerService::sendMessage);
        return creditos;
    }

}