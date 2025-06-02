package org.example.foodee_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.example.foodee_service.dto.response.common.ResultObject;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class ErrorController extends AbstractErrorController {

    public ErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping
    public ResponseEntity<ResultObject<Void>> handleError(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        
        ResultObject<Void> errorResponse = ResultObject.<Void>builder()
                .status(status.value())
                .code(String.valueOf(status.value()))
                .message(status.getReasonPhrase())
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();
        
        return ResponseEntity.status(status).body(errorResponse);
    }
} 