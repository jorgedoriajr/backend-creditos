package br.com.infuse.backendcreditos.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({CreditoNotFoundException.class, NfseNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex) {
        return ResponseEntity.notFound().build();
    }
}