package com.kodlama.io.inventoryservice.business.dto.responses.get.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class GetAllModelsResponse {
    private UUID id;
    private UUID brandId;
    private String name;
}