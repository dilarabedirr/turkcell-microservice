package com.kodlamaio.maintenanceservice.api.clients;

import com.kodlama.io.commonpackage.utils.dto.ClientResponse;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "inventory-service", fallback = CarClientFallback.class)
public interface CarClient {
    @Retry(name = "retry-maintenance")
    @GetMapping(value = "/api/cars/check-if-car-is-available/{carId}")
    ClientResponse checkIfCarIsAvailable(@PathVariable UUID carId);
}
