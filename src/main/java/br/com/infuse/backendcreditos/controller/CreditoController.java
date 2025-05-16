package br.com.infuse.backendcreditos.controller;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.service.CreditoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api")
@RestController
public class CreditoController {

    private final CreditoService creditoService;

    public CreditoController(CreditoService creditoService) {
        this.creditoService = creditoService;
    }

    @GetMapping("/creditos/credito/{numeroCredito}")
    List<CreditoDTO> findByNumeroCredito(@PathVariable String numeroCredito) {
        return creditoService.findByNumeroCredito(numeroCredito);
    }

    @GetMapping("/creditos/{numeroNfse}")
    List<CreditoDTO> findByNumeroNfse(@PathVariable String numeroNfse) {
        return creditoService.findByNumeroNfse(numeroNfse);
    }

}