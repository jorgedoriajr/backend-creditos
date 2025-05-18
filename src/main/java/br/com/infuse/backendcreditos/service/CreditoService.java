package br.com.infuse.backendcreditos.service;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.dto.DTOConverter;
import br.com.infuse.backendcreditos.exception.CreditoNotFoundException;
import br.com.infuse.backendcreditos.exception.NfseNotFoundException;
import br.com.infuse.backendcreditos.model.Credito;
import br.com.infuse.backendcreditos.repository.CreditoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditoService {

    private final CreditoRepository creditoRepository;

    public CreditoService(CreditoRepository creditoRepository) {
        this.creditoRepository = creditoRepository;
    }

    public List<CreditoDTO> findByNumeroCredito(String numeroCredito) {
        List<Credito> credito = creditoRepository.findByNumeroCredito(numeroCredito);
        if (credito == null || credito.isEmpty()) {
            throw new CreditoNotFoundException();
        }
        return credito
                .stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

    public List<CreditoDTO> findByNumeroNfse(String numeroNfse) {
        List<Credito> credito = creditoRepository.findByNumeroNfse(numeroNfse);
        if (credito == null || credito.isEmpty()) {
            throw new NfseNotFoundException();
        }
        return credito
                .stream()
                .map(DTOConverter::convert)
                .collect(Collectors.toList());
    }

}