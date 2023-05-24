package com.kodlamaio.paymentservice.business.concretes;

import com.kodlama.io.commonpackage.kafka.producer.KafkaProducer;
import com.kodlama.io.commonpackage.utils.dto.ClientResponse;
import com.kodlama.io.commonpackage.utils.dto.CreateRentalPaymentRequest;
import com.kodlama.io.commonpackage.utils.exceptions.BusinessException;
import com.kodlama.io.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.paymentservice.business.abstracts.PaymentService;
import com.kodlamaio.paymentservice.business.dto.requests.CreatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.requests.UpdatePaymentRequest;
import com.kodlamaio.paymentservice.business.dto.responses.CreatePaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.GetAllPaymentsResponse;
import com.kodlamaio.paymentservice.business.dto.responses.GetPaymentResponse;
import com.kodlamaio.paymentservice.business.dto.responses.UpdatePaymentResponse;
import com.kodlamaio.paymentservice.business.rules.PaymentBusinessRules;
import com.kodlamaio.paymentservice.entities.Payment;
import com.kodlamaio.paymentservice.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentManager implements PaymentService {
    private final ModelMapperService mapper;
    private final KafkaProducer producer;
    private final PaymentRepository repository;
    private final PaymentBusinessRules rules;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        var payments = repository.findAll();

        return payments
                .stream()
                .map(payment -> mapper.forResponse().map(payment, GetAllPaymentsResponse.class))
                .toList();
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        rules.checkIfPaymentExists(id);
        var payment = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(payment, GetPaymentResponse.class);

        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        var payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(null);
        repository.save(payment);

        var response = mapper.forResponse().map(payment, CreatePaymentResponse.class);
        return response;
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        rules.checkIfPaymentExists(id);
        var payment = mapper.forRequest().map(request, Payment.class);
        payment.setId(id);
        repository.save(payment);

        var response = mapper.forResponse().map(payment, UpdatePaymentResponse.class);
        return response;
    }


    @Override
    public void delete(UUID id) {
        rules.checkIfPaymentExists(id);
        repository.deleteById(id);
    }


    @Override
    public ClientResponse processRentalPayment(CreateRentalPaymentRequest request) {
        var response = new ClientResponse();
        try {
            rules.checkIfPaymentIsValid(request);
            Payment payment = repository.findByCardNumber(request.getCardNumber());
            rules.checkIfBalanceIfEnough(payment.getBalance(), request.getPrice());
            payment.setBalance(payment.getBalance() - request.getPrice());

            repository.save(payment);
            response.setSuccess(true);
        } catch (BusinessException exception) {
            response.setSuccess(false);
            response.setMessage(exception.getMessage());
        }
        return response;
    }
}
