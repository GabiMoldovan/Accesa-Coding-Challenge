package com.example.demo.exceptions;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }

    public static class NotFoundException extends RuntimeException {
        public NotFoundException(String message) {super(message);}
    }

    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException(String message) {super(message);}
    }
}
