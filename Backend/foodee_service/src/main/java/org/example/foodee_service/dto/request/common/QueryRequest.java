package org.example.foodee_service.dto.request.common;

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
public class QueryRequest {
    private List<Order> orders = new ArrayList<>();
    private List<Filter> filters = new ArrayList<>();
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class Order {
        private String field;
        private String direction = "asc";
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class Filter {
        private String field;
        private String operator;
        private String value;
    }
} 