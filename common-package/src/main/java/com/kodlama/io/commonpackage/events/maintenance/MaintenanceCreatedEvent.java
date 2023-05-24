package com.kodlama.io.commonpackage.events.maintenance;

import com.kodlama.io.commonpackage.events.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCreatedEvent implements Event {
    private UUID carId;
}
