package com.kodlama.io.inventoryservice.repository;

import com.kodlama.io.inventoryservice.entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModelRepository extends JpaRepository<Model, UUID> {
}
