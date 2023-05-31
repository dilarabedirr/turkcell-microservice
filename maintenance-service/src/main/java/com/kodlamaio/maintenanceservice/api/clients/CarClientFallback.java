package com.kodlamaio.maintenanceservice.api.clients;

import com.kodlama.io.commonpackage.utils.dto.ClientResponse;
import com.kodlama.io.commonpackage.utils.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
public class CarClientFallback implements CarClient {
    @Override
    public ClientResponse checkIfCarIsAvailable(UUID carId) {
        log.info("INVENTORY IS DOWN-MAINTENANCE!");
        throw new BusinessException("INVENTORY-SERVICE IS NOT AVAILABLE RIGHT NOW-MAINTENANCE!");
    }
}
