package com.kodlama.io.inventoryservice.business.dto.requests.update;

import com.kodlama.io.inventoryservice.entities.enums.State;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCarRequest {
    @NotNull
    @NotBlank
    private UUID modelId;
    @Min(value = 2000)
    // TODO: NotFuture custom annotation
    private int modelYear;
    @NotNull
    @NotBlank
    // TODO: Add Regex
    private String plate;
    @NotNull
    @NotBlank
    private State state;
    @Min(value = 1)
    private double dailyPrice;
}
