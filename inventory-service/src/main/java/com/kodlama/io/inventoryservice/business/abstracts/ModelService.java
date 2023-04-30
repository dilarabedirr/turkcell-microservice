package com.kodlama.io.inventoryservice.business.abstracts;

import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.model.GetAllModelsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.model.GetModelResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateModelResponse;

import java.util.List;
import java.util.UUID;

public interface ModelService {
    List<GetAllModelsResponse> getAll();
    GetModelResponse getById(UUID id);
    CreateModelResponse add(CreateModelRequest request);
    UpdateModelResponse update(UUID id, UpdateModelRequest request);
    void delete(UUID id);
}
