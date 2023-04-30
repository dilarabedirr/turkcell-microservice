package com.kodlama.io.inventoryservice.business.abstracts;

import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.car.GetAllCarsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.car.GetCarResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateCarResponse;

import java.util.List;
import java.util.UUID;

public interface CarService {
    List<GetAllCarsResponse> getAll();
    GetCarResponse getById(UUID id);
    CreateCarResponse add(CreateCarRequest request);
    UpdateCarResponse update(UUID id, UpdateCarRequest request);
    void delete(UUID id);
}
