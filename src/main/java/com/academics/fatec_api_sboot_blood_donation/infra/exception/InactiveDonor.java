package com.academics.fatec_api_sboot_blood_donation.infra.exception;

public class InactiveDonor extends RuntimeException {
    public InactiveDonor(String message) {
        super(message);
    }
}
