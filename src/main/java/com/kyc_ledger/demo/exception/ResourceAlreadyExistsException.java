package com.kyc_ledger.demo.exception;

public class ResourceAlreadyExistsException extends RuntimeException{

    public ResourceAlreadyExistsException(String message) {
        super(message);
    }
    public ResourceAlreadyExistsException(String resource, String field, String value) {
        super(resource + "already exists with " + field + ": "+ value);
    }

}
