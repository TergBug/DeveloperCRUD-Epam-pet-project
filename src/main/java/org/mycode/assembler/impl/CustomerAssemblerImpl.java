package org.mycode.assembler.impl;

import org.mycode.assembler.CustomerAssembler;
import org.mycode.dto.CustomerDto;
import org.mycode.exceptions.NoSuchEntryException;
import org.mycode.model.Customer;
import org.mycode.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
public class CustomerAssemblerImpl implements CustomerAssembler {
    private CustomerRepository customerRepository;

    @Autowired
    public void setCustomerRepository(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer assemble(CustomerDto customerDto) {
        try {
            return new Customer(customerDto.getId(), customerDto.getName(),
                    customerRepository.getById(customerDto.getId()).getProjects());
        } catch (NoSuchEntryException e) {
            return new Customer(customerDto.getId(), customerDto.getName(), new HashSet<>());
        }
    }

    @Override
    public CustomerDto assemble(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName());
    }
}
