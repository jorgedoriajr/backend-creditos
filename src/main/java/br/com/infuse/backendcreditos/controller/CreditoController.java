package br.com.infuse.backendcreditos.controller;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.exception.CreditoNotFoundException;
import br.com.infuse.backendcreditos.exception.NfseNotFoundException;
import br.com.infuse.backendcreditos.service.CreditoProducerService;
import br.com.infuse.backendcreditos.service.CreditoService;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<List<CreditoDTO>> findByNumeroCredito(@PathVariable String numeroCredito) {
        try {
            List<CreditoDTO> creditos = creditoService.findByNumeroCredito(numeroCredito);
            creditos.forEach(producerService::sendMessage);
            return ResponseEntity.ok(creditos);
        } catch (CreditoNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/creditos/{numeroNfse}")
    ResponseEntity<List<CreditoDTO>> findByNumeroNfse(@PathVariable String numeroNfse) {
        try {
            List<CreditoDTO> creditos = creditoService.findByNumeroNfse(numeroNfse);
            creditos.forEach(producerService::sendMessage);
            return ResponseEntity.ok(creditos);
        } catch (NfseNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}