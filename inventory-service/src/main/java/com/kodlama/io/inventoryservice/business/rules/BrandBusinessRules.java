package com.kodlama.io.inventoryservice.business.rules;

import com.kodlama.io.commonpackage.utils.exceptions.BusinessException;
import com.kodlama.io.inventoryservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandBusinessRules {
    private final BrandRepository repository;

    public void checkIfBrandExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("BRAND_NOT_EXISTS");
        }
    }
}
