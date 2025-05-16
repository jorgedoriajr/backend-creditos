package br.com.infuse.backendcreditos.service;

import br.com.infuse.backendcreditos.dto.CreditoDTO;
import br.com.infuse.backendcreditos.exception.CreditoNotFoundException;
import br.com.infuse.backendcreditos.exception.NfseNotFoundException;
import br.com.infuse.backendcreditos.model.Credito;
import br.com.infuse.backendcreditos.repository.CreditoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreditoServiceTest {

    @Mock
    private CreditoRepository creditoRepository;

    @InjectMocks
    private CreditoService creditoService;

    @Test
    public void findByNumeroCreditoDeveRetornarListaDeCreditoDTOQuandoEncontrado() {
        // Arrange
        String numeroCredito = "123";
        Credito credito = new Credito(1L, numeroCredito, "NFE123",
                LocalDate.now(), BigDecimal.TEN, "TIPO1", false,
                BigDecimal.valueOf(5), BigDecimal.valueOf(1000),
                BigDecimal.valueOf(100), BigDecimal.valueOf(900));

        List<Credito> creditos = List.of(credito);

        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(creditos);

        // Act
        List<CreditoDTO> resultado = creditoService.findByNumeroCredito(numeroCredito);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(creditoRepository, times(1)).findByNumeroCredito(numeroCredito);
    }

    @Test
    public void findByNumeroCreditoDeveLancarExcecaoQuandoNaoEncontrado() {
        // Arrange
        String numeroCredito = "456";
        when(creditoRepository.findByNumeroCredito(numeroCredito)).thenReturn(null);

        // Act & Assert
        assertThrows(CreditoNotFoundException.class, () -> {
            creditoService.findByNumeroCredito(numeroCredito);
        });
        verify(creditoRepository, times(1)).findByNumeroCredito(numeroCredito);
    }

    @Test
    public void findByNumeroNfseDeveRetornarListaDeCreditoDTOQuandoEncontrado() {
        // Arrange
        String numeroNfse = "NFE123";
        Credito credito = new Credito(1L, "123", numeroNfse,
                LocalDate.now(), BigDecimal.TEN, "TIPO1", false,
                BigDecimal.valueOf(5), BigDecimal.valueOf(1000),
                BigDecimal.valueOf(100), BigDecimal.valueOf(900));

        List<Credito> creditos = List.of(credito);

        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(creditos);

        // Act
        List<CreditoDTO> resultado = creditoService.findByNumeroNfse(numeroNfse);

        // Assert
        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        verify(creditoRepository, times(1)).findByNumeroNfse(numeroNfse);
    }

    @Test
    public void findByNumeroNfseDeveLancarExcecaoQuandoNaoEncontrado() {
        // Arrange
        String numeroNfse = "NFE456";
        when(creditoRepository.findByNumeroNfse(numeroNfse)).thenReturn(null);

        // Act & Assert
        assertThrows(NfseNotFoundException.class, () -> {
            creditoService.findByNumeroNfse(numeroNfse);
        });
        verify(creditoRepository, times(1)).findByNumeroNfse(numeroNfse);
    }
}