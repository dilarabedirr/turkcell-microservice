package com.kodlamaio.rentalservice.business.concretes;

import com.kodlama.io.commonpackage.events.invoice.InvoiceCreatedEvent;
import com.kodlama.io.commonpackage.events.rental.RentalCreatedEvent;
import com.kodlama.io.commonpackage.events.rental.RentalDeletedEvent;
import com.kodlama.io.commonpackage.kafka.producer.KafkaProducer;
import com.kodlama.io.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.kodlama.io.commonpackage.utils.dto.GetCarResponse;
import com.kodlama.io.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.rentalservice.api.clients.CarClient;
import com.kodlamaio.rentalservice.business.abstracts.RentalService;
import com.kodlamaio.rentalservice.business.dto.requests.CreateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.requests.UpdateRentalRequest;
import com.kodlamaio.rentalservice.business.dto.responses.CreateRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetAllRentalsResponse;
import com.kodlamaio.rentalservice.business.dto.responses.GetRentalResponse;
import com.kodlamaio.rentalservice.business.dto.responses.UpdateRentalResponse;
import com.kodlamaio.rentalservice.business.rules.RentalBusinessRules;
import com.kodlamaio.rentalservice.entities.Rental;
import com.kodlamaio.rentalservice.repository.RentalRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RentalManager implements RentalService {
    private final RentalRepository repository;
    private final ModelMapperService mapper;
    private final RentalBusinessRules rules;
    private final KafkaProducer producer;
    private final CarClient carClient;

    @Override
    public List<GetAllRentalsResponse> getAll() {
        var rentals = repository.findAll();
        var response = rentals
                .stream()
                .map(rental -> mapper.forResponse().map(rental, GetAllRentalsResponse.class))
                .toList();

        return response;
    }

    @Override
    public GetRentalResponse getById(UUID id) {
        rules.checkIfRentalExists(id);
        var rental = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(rental, GetRentalResponse.class);

        return response;
    }

    @Override
    public CreateRentalResponse add(CreateRentalRequest request) {
        rules.ensureCarIsAvailable(request.getCarId());
        var rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(null);
        rental.setTotalPrice(getTotalPrice(rental));
        rental.setRentedAt(LocalDate.now());

        CreateRentalPaymentRequest rentalPaymentRequest = new CreateRentalPaymentRequest();
        mapper.forRequest().map(request.getPaymentRequest(), rentalPaymentRequest);
        rentalPaymentRequest.setPrice(rental.getTotalPrice());

        rules.ensurePaymentIsAvailable(rentalPaymentRequest);

        repository.save(rental);
        sendKafkaRentalCreatedEvent(request.getCarId());
        var response = mapper.forResponse().map(rental, CreateRentalResponse.class);

        return response;
    }

    @Override
    public UpdateRentalResponse update(UUID id, UpdateRentalRequest request) {
        rules.checkIfRentalExists(id);
        var rental = mapper.forRequest().map(request, Rental.class);
        rental.setId(id);
        repository.save(rental);
        var response = mapper.forResponse().map(rental, UpdateRentalResponse.class);

        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfRentalExists(id);
        sendKafkaRentalDeletedEvent(id);
        repository.deleteById(id);
    }

    private double getTotalPrice(Rental rental) {
        return rental.getDailyPrice() * rental.getRentedForDays();
    }

    private void sendKafkaRentalCreatedEvent(UUID carId) {
        producer.sendMessage(new RentalCreatedEvent(carId), "rental-created");
    }

    private void sendKafkaRentalDeletedEvent(UUID id) {
        var carId=repository.findById(id).orElseThrow().getCarId();
        producer.sendMessage(new RentalDeletedEvent(carId), "rental-deleted");
    }

    private void sendKafkaInvoiceCreatedEvent(InvoiceCreatedEvent event){
        producer.sendMessage(event,"invoice-created");
    }

    private void mergeRequest(CreateRentalRequest request,InvoiceCreatedEvent event,Rental rental){
        GetCarResponse response = carClient.getById(request.getCarId());
        event.setBrandName(response.getBrandName());
        event.setPlate(response.getPlate());
        event.setModelName(response.getModelName());
        event.setRentedAt(rental.getRentedAt());
        event.setTotalPrice(rental.getTotalPrice());
        event.setDailyPrice(rental.getDailyPrice());
        event.setCardHolder(request.getPaymentRequest().getCardHolder());
        event.setRentedForDays(rental.getRentedForDays());
    }
}
