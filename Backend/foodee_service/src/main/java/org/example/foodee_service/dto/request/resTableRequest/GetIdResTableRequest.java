package org.example.foodee_service.dto.request.resTableRequest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetIdResTableRequest {
    private Long resTableId;
}
