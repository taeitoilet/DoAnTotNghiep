package org.example.foodee_service.dto.response.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Result<T> {
    private int status = 0;
    private String code = "0";
    private String message = "Success";
    private String traceId;
    private List<Error> errors = new ArrayList<>();
    private T data;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class Error {
        private String errorCode;
        private String errorDesc;
        private Object refVal;
    }
    
    public static <T> Result<T> success(T data) {
        return Result.<T>builder()
                .status(0)
                .code("0")
                .message("Success")
                .data(data)
                .build();
    }
    
    public static <T> Result<T> error(int status, String code, String message) {
        return Result.<T>builder()
                .status(status)
                .code(code)
                .message(message)
                .build();
    }
    
    public static <T> Result<T> error(int status, String code, String message, List<Error> errors) {
        return Result.<T>builder()
                .status(status)
                .code(code)
                .message(message)
                .errors(errors)
                .build();
    }
} 