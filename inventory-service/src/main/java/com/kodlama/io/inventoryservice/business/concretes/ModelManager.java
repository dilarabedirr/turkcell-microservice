package com.kodlama.io.inventoryservice.business.concretes;

import com.kodlama.io.inventoryservice.business.abstracts.ModelService;
import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateModelRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateModelRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateModelResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.model.GetAllModelsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.model.GetModelResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateModelResponse;

import java.util.List;
import java.util.UUID;

public class ModelManager implements ModelService {

    @Override
    public List<GetAllModelsResponse> getAll() {
        return null;
    }

    @Override
    public GetModelResponse getById(UUID id) {
        return null;
    }

    @Override
    public CreateModelResponse add(CreateModelRequest request) {
        return null;
    }

    @Override
    public UpdateModelResponse update(UUID id, UpdateModelRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
