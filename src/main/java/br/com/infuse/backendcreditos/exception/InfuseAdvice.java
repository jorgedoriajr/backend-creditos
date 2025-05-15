package br.com.infuse.backendcreditos.exception;

import br.com.infuse.backendcreditos.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;

@ControllerAdvice(basePackages = "br.com.infuse.backendcreditos.controller")
public class InfuseAdvice {

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CreditoNotFoundException.class)
    public ErrorDTO handleCreditoNotFound(CreditoNotFoundException creditoNotFoundException)  {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorDTO.setMessage("Crédito não encontrado!");
        errorDTO.setTimestamp(LocalDateTime.now());
        return errorDTO;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NfseNotFoundException.class)
    public ErrorDTO handleNfseNotFound(NfseNotFoundException nfseNotFoundException )  {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setStatus(HttpStatus.NOT_FOUND.value());
        errorDTO.setMessage("Nota Fiscal Eletrônica não encontrada!");
        errorDTO.setTimestamp(LocalDateTime.now());
        return errorDTO;
    }

}