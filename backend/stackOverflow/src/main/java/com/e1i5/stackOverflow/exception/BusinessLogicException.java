package com.e1i5.stackOverflow.exception;

import lombok.Getter;

public class BusinessLogicException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;

<<<<<<< HEAD
    public BusinessLogicException(ExceptionCode exceptionCode){
=======
    public BusinessLogicException(ExceptionCode exceptionCode) {
>>>>>>> afa8f86d9c61d5c805df18d7b9b827f01f5164d2
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
}
