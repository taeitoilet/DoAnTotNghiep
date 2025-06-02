package org.example.foodee_service.service;

import org.example.foodee_service.dto.request.common.PagingQueryRequest;
import org.example.foodee_service.dto.request.resTableRequest.CreateResTableRequest;
import org.example.foodee_service.dto.request.resTableRequest.GetIdResTableRequest;
import org.example.foodee_service.dto.request.resTableRequest.UpdateResTableRequest;
import org.example.foodee_service.dto.response.resTableResponse.ResTableResponseDto;
import org.example.foodee_service.entity.ResTable;

import java.util.List;

public interface ResTableService {
    ResTableResponseDto createResTable(CreateResTableRequest request);
    ResTableResponseDto updateResTable(UpdateResTableRequest request);
    void deleteResTable(GetIdResTableRequest request);
    List<ResTableResponseDto> getResTable(PagingQueryRequest request, long[] total);
    ResTableResponseDto isAvailableResTable(GetIdResTableRequest request);
}
