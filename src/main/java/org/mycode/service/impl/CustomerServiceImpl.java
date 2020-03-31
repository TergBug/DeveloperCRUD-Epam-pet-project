package org.mycode.service.impl;

import org.apache.log4j.Logger;
import org.mycode.assembler.CustomerAssembler;
import org.mycode.dto.CustomerDto;
import org.mycode.repository.CustomerRepository;
import org.mycode.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = Logger.getLogger(CustomerServiceImpl.class);
    private CustomerRepository currentRepo;
    private CustomerAssembler customerAssembler;

    @Autowired
    public CustomerServiceImpl(CustomerRepository currentRepo, CustomerAssembler customerAssembler) {
        this.currentRepo = currentRepo;
        this.customerAssembler = customerAssembler;
    }

    public void create(CustomerDto model) {
        currentRepo.create(customerAssembler.assemble(model));
        log.debug("Service->Create");
    }

    public CustomerDto getById(UUID readID) {
        CustomerDto customer = customerAssembler.assemble(currentRepo.getById(readID));
        log.debug("Service->Read");
        return customer;
    }

    public void update(CustomerDto updatedModel) {
        currentRepo.update(customerAssembler.assemble(updatedModel));
        log.debug("Service->Update");
    }

    public void delete(UUID deletedEntry) {
        currentRepo.delete(deletedEntry);
        log.debug("Service->Delete");
    }

    public List<CustomerDto> getAll() {
        List<CustomerDto> customers = currentRepo.getAll().stream().map(el -> customerAssembler.assemble(el))
                .collect(Collectors.toList());
        log.debug("Service->Get all");
        return customers;
    }
}
