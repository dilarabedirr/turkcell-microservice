package com.kodlama.io.inventoryservice.business.rules;

import com.kodlama.io.commonpackage.utils.exceptions.BusinessException;
import com.kodlama.io.inventoryservice.repository.BrandRepository;
import com.kodlama.io.inventoryservice.repository.ModelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ModelBusinessRules {
    private final ModelRepository repository;

    public void checkIfModelExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("MODEL_NOT_EXISTS");
        }
    }
}
