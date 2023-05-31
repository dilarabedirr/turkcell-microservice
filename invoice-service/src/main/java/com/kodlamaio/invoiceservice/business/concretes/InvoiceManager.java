package com.kodlamaio.invoiceservice.business.concretes;

import com.kodlama.io.commonpackage.utils.mappers.ModelMapperService;
import com.kodlamaio.invoiceservice.business.abstracts.InvoiceService;
import com.kodlamaio.invoiceservice.business.dto.responses.GetAllInvoicesResponse;
import com.kodlamaio.invoiceservice.business.dto.responses.GetInvoiceResponse;
import com.kodlamaio.invoiceservice.entities.Invoice;
import com.kodlamaio.invoiceservice.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class InvoiceManager implements InvoiceService {
    private final InvoiceRepository repository;
    private final ModelMapperService mapper;

    @Override
    public List<GetAllInvoicesResponse> getAll() {
        var invoices = repository.findAll();
        var response = invoices.stream()
                .map(invoice -> mapper.forResponse().map(invoice, GetAllInvoicesResponse.class))
                .toList();
        return response;
    }

    @Override
    public GetInvoiceResponse getById(String id) {
        var invoice = repository.findById(id).orElseThrow();
        var response = mapper.forResponse().map(invoice, GetInvoiceResponse.class);
        return response;
    }

    @Override
    public void add(Invoice invoice) {
        invoice.setId(UUID.randomUUID().toString());
        repository.save(invoice);
    }

    @Override
    public void delete(String id) {
        repository.deleteById(id);
    }
}
