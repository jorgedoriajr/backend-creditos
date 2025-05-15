package br.com.infuse.backendcreditos.service;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.dto.DTOConverter;
import br.com.infuse.backendcreditos.exception.CreditoNotFoundException;
import br.com.infuse.backendcreditos.exception.NfseNotFoundException;
import br.com.infuse.backendcreditos.model.Credito;
import br.com.infuse.backendcreditos.repository.CreditoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreditoService {

    private final CreditoRepository creditoRepository;

    public CreditoService(CreditoRepository creditoRepository) {
        this.creditoRepository = creditoRepository;
    }

    public Optional<CreditoDTO> findByNumeroCredito(String numeroCredito) {
        Credito credito = creditoRepository.findByNumeroCredito(numeroCredito);
        if (credito == null) {
            throw new CreditoNotFoundException();
        }
        return Optional.of(DTOConverter.convert(credito));
    }

    public Optional<CreditoDTO> findByNumeroNfse(String numeroNfse) {
        Credito credito = creditoRepository.findByNumeroNfse(numeroNfse);
        if (credito == null) {
            throw new NfseNotFoundException();
        }
        return Optional.of(DTOConverter.convert(credito));
    }

}