package com.kodlama.io.inventoryservice.api.controllers;

import com.kodlama.io.inventoryservice.business.abstracts.BrandService;
import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.brand.GetAllBrandsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.brand.GetBrandResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateBrandResponse;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/brands")
@AllArgsConstructor
public class BrandsController {
    private final BrandService service;

    @GetMapping
    public List<GetAllBrandsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetBrandResponse getById(@PathVariable UUID id) {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateBrandResponse add(@RequestBody CreateBrandRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateBrandResponse update(@PathVariable UUID id, @RequestBody UpdateBrandRequest brand) {
        return service.update(id, brand);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
