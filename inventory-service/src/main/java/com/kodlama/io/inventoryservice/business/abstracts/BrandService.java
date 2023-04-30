package com.kodlama.io.inventoryservice.business.abstracts;

import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.brand.GetAllBrandsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.brand.GetBrandResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateBrandResponse;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<GetAllBrandsResponse> getAll();
    GetBrandResponse getById(UUID id);
    CreateBrandResponse add(CreateBrandRequest request);
    UpdateBrandResponse update(UUID id, UpdateBrandRequest request);
    void delete(UUID id);
}
