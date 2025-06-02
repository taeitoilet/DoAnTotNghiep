package org.example.foodee_service.dto.response.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultObject<T> {
    private int status = 0;
    private String code = "0";
    private String message = "Success";
    private String traceId;
    private List<ErrorDetail> errors = new ArrayList<>();
    private T data;
    private LocalDateTime timestamp;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ErrorDetail {
        private String errorCode;
        private String errorDesc;
        private Object refVal;
    }
    
    public static <T> ResultObject<T> success(T data) {
        return ResultObject.<T>builder()
                .status(0)
                .code("0")
                .message("Success")
                .traceId(UUID.randomUUID().toString())
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    public static <T> ResultObject<T> error(int status, String code, String message) {
        return ResultObject.<T>builder()
                .status(status)
                .code(code)
                .message(message)
                .traceId(UUID.randomUUID().toString())
                .timestamp(LocalDateTime.now())
                .build();
    }
    
    public static <T> ResultObject<T> error(int status, String code, String message, List<ErrorDetail> errors) {
        return ResultObject.<T>builder()
                .status(status)
                .code(code)
                .message(message)
                .traceId(UUID.randomUUID().toString())
                .errors(errors)
                .timestamp(LocalDateTime.now())
                .build();
    }
} 