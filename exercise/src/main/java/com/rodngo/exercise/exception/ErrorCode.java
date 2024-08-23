package com.rodngo.exercise.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999,"UNCATEGORIZED EXCEPTION",HttpStatus.INTERNAL_SERVER_ERROR),
    UNAUTHENTICATED(9997,"Unauthenticated",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(9996,"You do not have permission",HttpStatus.FORBIDDEN),
    ID_DOES_NOT_EXIST(9991,"This id doesn't exist!",HttpStatus.NOT_FOUND),
    USER_NOT_EXIST(9990,"This user doesn't exist!",HttpStatus.NOT_FOUND),
    USERNAME_EXIST(9989,"This username is already available",HttpStatus.INTERNAL_SERVER_ERROR),
    CUSTOMER_NOT_FOUND(9988,"Customer not found",HttpStatus.NOT_FOUND);
    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
