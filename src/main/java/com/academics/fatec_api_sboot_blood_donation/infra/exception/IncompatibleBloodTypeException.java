package com.academics.fatec_api_sboot_blood_donation.infra.exception;

public class IncompatibleBloodTypeException extends RuntimeException {
    public IncompatibleBloodTypeException(String message) {
        super(message);
    }
}
