package com.kodlama.io.inventoryservice.business.concretes;

import com.kodlama.io.commonpackage.events.inventory.BrandDeletedEvent;
import com.kodlama.io.commonpackage.kafka.producer.KafkaProducer;
import com.kodlama.io.commonpackage.utils.mappers.ModelMapperService;
import com.kodlama.io.inventoryservice.business.abstracts.BrandService;
import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateBrandRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateBrandRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateBrandResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.brand.GetAllBrandsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.brand.GetBrandResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateBrandResponse;
import com.kodlama.io.inventoryservice.business.rules.BrandBusinessRules;
import com.kodlama.io.inventoryservice.entities.Brand;
import com.kodlama.io.inventoryservice.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
    private final BrandRepository repository;
    private final ModelMapperService mapper;
    private final BrandBusinessRules rules;
    private final KafkaProducer producer;

    @Override
    public List<GetAllBrandsResponse> getAll() {
        var brands = repository.findAll();
        var response = brands
                .stream()
                .map(brand -> mapper.forResponse().map(brand, GetAllBrandsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetBrandResponse getById(UUID id) {
        rules.checkIfBrandExists(id);
        var brand = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(brand, GetBrandResponse.class);

        return response;
    }

    @Override
    public CreateBrandResponse add(CreateBrandRequest request) {
        var brand = mapper.forRequest().map(request, Brand.class);
        repository.save(brand);
        var response = mapper.forResponse().map(brand, CreateBrandResponse.class);

        return response;
    }

    @Override
    public UpdateBrandResponse update(UUID id, UpdateBrandRequest request) {
        rules.checkIfBrandExists(id);
        var brand = mapper.forRequest().map(request, Brand.class);
        brand.setId(id);
        repository.save(brand);
        var response = mapper.forResponse().map(brand, UpdateBrandResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfBrandExists(id);
        repository.deleteById(id);
        sendKafkaBrandDeletedEvent(id);
    }

    private void sendKafkaBrandDeletedEvent(UUID id) {
        producer.sendMessage(new BrandDeletedEvent(id), "brand-deleted");
    }
}
