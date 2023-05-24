package com.kodlama.io.inventoryservice.business.kafka.consumer;


import com.kodlama.io.commonpackage.events.maintenance.MaintenanceCompletedEvent;
import com.kodlama.io.commonpackage.events.maintenance.MaintenanceCreatedEvent;
import com.kodlama.io.commonpackage.events.maintenance.MaintenanceDeletedEvent;
import com.kodlama.io.inventoryservice.business.abstracts.CarService;
import com.kodlama.io.inventoryservice.entities.enums.State;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MaintenanceConsumer {
    private final CarService service;

    @KafkaListener(topics = "maintenance-created", groupId = "inventory-maintenance-created")
    public void consume(MaintenanceCreatedEvent event) {
        service.changeStateByCarId(State.Maintenance, event.getCarId());
        log.info("Maintenance created event consumed {}", event);
    }

    @KafkaListener(topics = "maintenance-completed", groupId = "inventory-maintenance-completed")
    public void consume(MaintenanceCompletedEvent event) {
        service.changeStateByCarId(State.Available, event.getCarId());
        log.info("Maintenance completed event consumed {}", event);
    }

    @KafkaListener(topics = "maintenance-deleted", groupId = "inventory-maintenance-deleted")
    public void consume(MaintenanceDeletedEvent event) {
        service.changeStateByCarId(State.Available, event.getCarId());
        log.info("Maintenance deleted event consumed {}", event);
    }
}
