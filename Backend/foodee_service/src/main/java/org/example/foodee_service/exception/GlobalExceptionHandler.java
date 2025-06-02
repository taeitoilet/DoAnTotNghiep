package org.example.foodee_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.foodee_service.dto.response.common.ApiResponse;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    String exLog(Exception ex) {
        String classname = ex.getStackTrace()[0].getClassName();
        String methodName = ex.getStackTrace()[0].getMethodName();
        int lineNumber = ex.getStackTrace()[0].getLineNumber();
        String message = ex.getMessage();

        return ("Exception in class: " + classname + ", method: " + methodName + ", line: " + lineNumber + ", message: " + message);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ResultObject<Void>> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("Endpoint not found: {}", ex.getMessage());
        
        ResultObject<Void> response = ResultObject.<Void>builder()
                .status(HttpStatus.NOT_FOUND.value())
                .code("404")
                .message("Endpoint not found " + ex.getMessage())
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResultObject<Void>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        
        ResultObject<Void> response = ResultObject.<Void>builder()
                .status(1)
                .code("400")
                .message(ex.getMessage())
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ResultObject<Void>> handleResourceAlreadyExistsException(ResourceAlreadyExistsException ex) {
        log.error("Resource already exists: {}", ex.getMessage());
        
        ResultObject<Void> response = ResultObject.<Void>builder()
                .status(409)
                .code("409")
                .message(ex.getMessage())
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResultObject<Void>> handleGlobalException(Exception ex) {
        log.error("Unexpected error occurred: {}", ex.getMessage(), ex);
        
        ResultObject<Void> response = ResultObject.<Void>builder()
                .status(500)
                .code("500")
                .message("An unexpected error occurred")
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ResultObject<Void>> handleApplicationException(AppException ex) {

        List<ResultObject.ErrorDetail> errorDetails = List.of(ResultObject.ErrorDetail.builder()
                .errorCode(Integer.toString(ex.getAppError().getCode()))
                .errorDesc(ex.getAppError().getMessage())
                .build());

        ResultObject<Void> response = ResultObject.error(400, "400", "Validation failed", errorDetails);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultObject<Void>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation failed: {}", ex.getBindingResult().getFieldErrors().stream().map(error -> error.getField() + " " + error.getDefaultMessage()).collect(Collectors.joining(", ")));

        List<ResultObject.ErrorDetail> errorDetails = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ResultObject.ErrorDetail.builder()
                        .errorCode(error.getField())
                        .errorDesc(error.getDefaultMessage())
                        .build())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        ResultObject<Void> response = ResultObject.error(400, "400", "Validation failed", errorDetails);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResultObject<Void>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid request body: {}", ex.getMessage());

        ResultObject<Void> response = ResultObject.<Void>builder()
                .status(400)
                .code("400")
                .message("Invalid request body")

                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
