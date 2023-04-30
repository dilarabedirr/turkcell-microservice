package com.kodlama.io.inventoryservice.business.dto.responses.get.brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetAllBrandsResponse {
    private UUID id;
    private String name;
}
