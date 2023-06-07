package com.kodlama.io.inventoryservice.api.controllers;

import com.kodlama.io.commonpackage.utils.constants.Roles;
import com.kodlama.io.commonpackage.utils.dto.ClientResponse;
import com.kodlama.io.inventoryservice.business.abstracts.CarService;
import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.car.GetAllCarsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.car.GetCarResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/cars")
public class CarsController {
    private final CarService service;

    @GetMapping
    //@Secured("ROLE_admin")
    @PreAuthorize(Roles.AdminAndUser) // SPeL
    public List<GetAllCarsResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @PostAuthorize("hasRole('admin') || returnObject.modelYear == 2019")
    public GetCarResponse getById(@PathVariable UUID id, @AuthenticationPrincipal Jwt jwt) {
        System.out.println(jwt.getClaims().get("email"));
        System.out.println(jwt.getClaims().get("sub"));
        System.out.println(jwt.getClaims().get("given_name"));
        System.out.println(jwt.getClaims().get("family_name"));

        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCarResponse add(@Valid @RequestBody CreateCarRequest request) {
        return service.add(request);
    }

    @PutMapping("/{id}")
    public UpdateCarResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateCarRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/check-car-available/{id}")
    public ClientResponse checkIfCarAvailable(@PathVariable UUID id) {
        return service.checkIfCarAvailable(id);
    }

}
