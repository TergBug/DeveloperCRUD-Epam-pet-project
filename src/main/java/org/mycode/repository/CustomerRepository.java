package org.mycode.repository;

import org.mycode.model.Customer;

import java.util.UUID;

public interface CustomerRepository extends GenericRepository<Customer, UUID> {
}
