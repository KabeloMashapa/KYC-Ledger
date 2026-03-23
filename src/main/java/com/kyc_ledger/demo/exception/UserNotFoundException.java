package com.kyc_ledger.demo.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(Long id) {
        super("User not found with ID : " + id);
    }
}
