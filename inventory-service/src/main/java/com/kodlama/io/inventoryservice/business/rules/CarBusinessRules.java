package com.kodlama.io.inventoryservice.business.rules;

import com.kodlama.io.commonpackage.utils.exceptions.BusinessException;
import com.kodlama.io.inventoryservice.entities.enums.State;
import com.kodlama.io.inventoryservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CarBusinessRules {
    private final CarRepository repository;

    public void checkIfCarExists(UUID id) {
        if (!repository.existsById(id)) {
            throw new BusinessException("CAR_NOT_EXISTS");
        }
    }

    public void checkCarAvailability(UUID id) {
        var car = repository.findById(id).orElseThrow();
        if (!car.getState().equals(State.Available)) {
            throw new BusinessException("CAR_NOT_AVAILABLE");
        }
    }
}
