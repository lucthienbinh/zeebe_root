package com.mainapplication.spring_boot.authentication_module.provider;

public class InvalidJwtAuthenticationException extends Exception {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public int getHttpStatus(){
        return 403;
    } 

    public InvalidJwtAuthenticationException(String errorMessage) {
        super(errorMessage);
    }
}
