package com.rodngo.exercise.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rodngo.exercise.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception){
        ApiResponse apiResponse = ApiResponse.builder()
                                            .code(ErrorCode.UNAUTHORIZED.getCode())
                                            .message(ErrorCode.UNAUTHORIZED.getMessage())
                                            .build();
        return ResponseEntity.status(ErrorCode.UNAUTHORIZED.getStatusCode())
                            .body(apiResponse);
    }


    //BadCredentialsException
    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = BadCredentialsException.class)
    ResponseEntity<ApiResponse> handlingBadCredentialsException(BadCredentialsException exception){
        ApiResponse apiResponse = ApiResponse.builder()
                                            .code(ErrorCode.UNAUTHENTICATED.getCode())
                                            .message(ErrorCode.UNAUTHENTICATED.getMessage())
                                            .build();
        return ResponseEntity.status(ErrorCode.UNAUTHENTICATED.getStatusCode())
                            .body(apiResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> handlingAppException(AppException exception){
        ApiResponse apiResponse = ApiResponse.builder()
                                            .code(exception.getErrorCode().getCode())
                                            .message(exception.getErrorCode().getMessage())
                                            .build(); 
        return ResponseEntity.status(exception.getErrorCode().getStatusCode())
                            .body(apiResponse);
    }

    @SuppressWarnings("rawtypes")
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> handlingRuntimeException(Exception exception){
        ApiResponse apiResponse = ApiResponse.builder()
                                            .code(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode())
                                            .message(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage())
                                            .build(); 
        return ResponseEntity.status(ErrorCode.UNCATEGORIZED_EXCEPTION.getStatusCode()).body(apiResponse);
    }
}
