package com.kodlama.io.inventoryservice.repository;

import com.kodlama.io.inventoryservice.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {
}
