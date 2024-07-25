package com.project.test.controller.advice;


import com.project.test.model.dto.GlobalResponse;
import com.project.test.model.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class AdviceController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<GlobalResponse> response (CustomException ex, WebRequest request){
    	GlobalResponse error = new GlobalResponse(ex.getErrorCode(), ex.getErrorMessage(), null);
        
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}
