package com.academics.fatec_api_sboot_blood_donation.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErro>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        var error = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(error.stream().map(DadosErro::new).toList());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> handleEntityNotFound() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IncompatibleBloodTypeException.class)
    public ResponseEntity<String> handleIncompatibleBloodType(IncompatibleBloodTypeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(AgeException.class)
    public ResponseEntity<String> hangleNotOldEnough(AgeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InactiveDonor.class)
    public ResponseEntity<String> handleInactiveDonor(InactiveDonor ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    private record DadosErro(String campo, String mensagem) {
        DadosErro(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }
}