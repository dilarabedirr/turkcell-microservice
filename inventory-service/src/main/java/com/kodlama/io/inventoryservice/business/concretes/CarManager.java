package com.kodlama.io.inventoryservice.business.concretes;

import com.kodlama.io.commonpackage.events.inventory.CarCreatedEvent;
import com.kodlama.io.commonpackage.events.inventory.CarDeletedEvent;
import com.kodlama.io.commonpackage.utils.mappers.ModelMapperService;
import com.kodlama.io.inventoryservice.business.abstracts.CarService;
import com.kodlama.io.inventoryservice.business.dto.requests.create.CreateCarRequest;
import com.kodlama.io.inventoryservice.business.dto.requests.update.UpdateCarRequest;
import com.kodlama.io.inventoryservice.business.dto.responses.create.CreateCarResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.car.GetAllCarsResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.get.car.GetCarResponse;
import com.kodlama.io.inventoryservice.business.dto.responses.update.UpdateCarResponse;
import com.kodlama.io.inventoryservice.business.kafka.producer.InventoryProducer;
import com.kodlama.io.inventoryservice.business.rules.CarBusinessRules;
import com.kodlama.io.inventoryservice.entities.Car;
import com.kodlama.io.inventoryservice.entities.enums.State;
import com.kodlama.io.inventoryservice.repository.CarRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CarManager implements CarService {

    private final CarRepository repository;
    private final ModelMapperService mapper;
    private final CarBusinessRules rules;
    private final InventoryProducer producer;

    @Override
    public List<GetAllCarsResponse> getAll() {
        var cars = repository.findAll();
        var response = cars
                .stream()
                .map(car -> mapper.forResponse().map(car, GetAllCarsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetCarResponse getById(UUID id) {
        rules.checkIfCarExists(id);
        var car = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(car, GetCarResponse.class);

        return response;
    }

    @Override
    public CreateCarResponse add(CreateCarRequest request) {
        var car = mapper.forRequest().map(request, Car.class);
        car.setId(UUID.randomUUID());
        car.setState(State.Available);
        var createdCar = repository.save(car);
        sendKafkaCarCreatedEvent(createdCar);
        var response = mapper.forResponse().map(createdCar, CreateCarResponse.class);

        return response;
    }

    @Override
    public UpdateCarResponse update(UUID id, UpdateCarRequest request) {
        rules.checkIfCarExists(id);
        var car = mapper.forRequest().map(request, Car.class);
        car.setId(id);
        repository.save(car);
        var response = mapper.forResponse().map(car, UpdateCarResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfCarExists(id);
        repository.deleteById(id);
        sendKafkaCarDeletedEvent(id);
    }

    @Override
    public void checkIfCarAvailable(UUID id) {
        rules.checkIfCarExists(id);
        rules.checkCarAvailability(id);
    }

    @Override
    public void changeStateByCarId(State state, UUID id) {
        repository.changeStateByCarId(state, id);
    }

    private void sendKafkaCarCreatedEvent(Car createdCar) {
        var event = mapper.forResponse().map(createdCar, CarCreatedEvent.class);
        producer.sendMessage(event);
    }

    private void sendKafkaCarDeletedEvent(UUID id) {
        producer.sendMessage(new CarDeletedEvent(id));
    }
}
