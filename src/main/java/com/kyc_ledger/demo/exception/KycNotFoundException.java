package com.kyc_ledger.demo.exception;

public class KycNotFoundException extends RuntimeException{

    public KycNotFoundException(String message) {
        super(message);
    }
    public KycNotFoundException(Long id) {
        super("KYC record not found with iD: "+ id);
    }
    public KycNotFoundException(String field,String value) {
        super("KYC record not found with " + field + ": "+value)
    }
}
