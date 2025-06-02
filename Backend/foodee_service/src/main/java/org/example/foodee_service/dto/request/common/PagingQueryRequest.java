package org.example.foodee_service.dto.request.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class PagingQueryRequest extends QueryRequest {
    private Page page;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @SuperBuilder
    public static class Page {
        private Integer pageSize = 20;
        private Integer pageNum = 1;
        private Boolean getTotal = true;
    }
}