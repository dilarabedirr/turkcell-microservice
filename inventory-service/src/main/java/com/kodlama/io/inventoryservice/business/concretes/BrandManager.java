package com.kodlama.io.inventoryservice.business.concretes;

import com.kodlama.io.inventoryservice.business.abstracts.BrandService;
import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.brand.GetAllBrandsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.brand.GetBrandResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateBrandResponse;

import java.util.List;
import java.util.UUID;

public class BrandManager implements BrandService {
    @Override
    public List<GetAllBrandsResponse> getAll() {
        return null;
    }

    @Override
    public GetBrandResponse getById(UUID id) {
        return null;
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        return null;
    }

    @Override
    public UpdateBrandResponse update(UUID id, UpdateBrandRequest request) {
        return null;
    }

    @Override
    public void delete(UUID id) {

    }
}
