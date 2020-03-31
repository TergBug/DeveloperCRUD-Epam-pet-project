package org.mycode.service;

import org.mycode.dto.CustomerDto;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    void create(CustomerDto model);

    CustomerDto getById(UUID readID);

    void update(CustomerDto updatedModel);

    void delete(UUID deletedEntry);

    List<CustomerDto> getAll();
}
