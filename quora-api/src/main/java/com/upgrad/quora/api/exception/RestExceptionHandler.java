package com.upgrad.quora.api.exception;

import com.upgrad.quora.api.model.ErrorResponse;
import com.upgrad.quora.service.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(SignUpRestrictedException.class)
    public ResponseEntity<ErrorResponse> signUpRestrictedException(SignUpRestrictedException sure, WebRequest request){

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(sure.getCode()).message(sure.getErrorMessage()), HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorResponse> authenticationFailedException(AuthenticationFailedException afe, WebRequest request){

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(afe.getCode()).message(afe.getErrorMessage()), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(SignOutRestrictedException.class)
    public ResponseEntity<ErrorResponse> signoutRestrictedException(SignOutRestrictedException sore, WebRequest request){

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(sore.getCode()).message(sore.getErrorMessage()), HttpStatus.CONFLICT
        );
    }

    //Question feature related exceptinos

    @ExceptionHandler(InvalidQuestionException.class)
    public ResponseEntity<ErrorResponse> authorizationFailedException(InvalidQuestionException iqe, WebRequest request){

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(iqe.getCode()).message(iqe.getErrorMessage()), HttpStatus.CONFLICT
        );
    }


    //Answer feature related exceptions

    @ExceptionHandler(AuthorizationFailedException.class)
    public ResponseEntity<ErrorResponse> authorizationFailedException(AuthorizationFailedException afe, WebRequest request){

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(afe.getCode()).message(afe.getErrorMessage()), HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(AnswerNotFoundException.class)
    public ResponseEntity<ErrorResponse> answerNotFoundException(AnswerNotFoundException anf, WebRequest request){

        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse().code(anf.getCode()).message(anf.getErrorMessage()), HttpStatus.CONFLICT
        );
    }


}
