package com.kodlama.io.inventoryservice.repository;

import com.kodlama.io.inventoryservice.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
}
