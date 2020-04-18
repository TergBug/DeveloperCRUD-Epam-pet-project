package org.mycode.service;

import org.junit.Test;
import org.mycode.assembler.CustomerAssembler;
import org.mycode.dto.CustomerDto;
import org.mycode.repository.CustomerRepository;
import org.mycode.service.impl.CustomerServiceImpl;

import java.util.UUID;

import static org.mockito.Mockito.*;

public class CustomerServiceTest {
    private static final UUID ID_FOR_CREATE = UUID.randomUUID();
    private static final UUID ID_FOR_GET = UUID.fromString("2936f525-5b05-47e2-8c08-f681c9ab1139");
    private static final UUID ID_FOR_DELETE = UUID.fromString("3025faf1-dc64-40ae-840b-58d5f711eeb6");
    private static final CustomerDto CREATE_CUSTOMER = new CustomerDto(ID_FOR_CREATE, "Customer1");
    private static final CustomerDto UPDATE_CUSTOMER = new CustomerDto(ID_FOR_CREATE, "Customer3");
    private CustomerRepository mockedRepo = mock(CustomerRepository.class);
    private CustomerAssembler customerAssembler = mock(CustomerAssembler.class);
    private CustomerServiceImpl sut = new CustomerServiceImpl(mockedRepo, customerAssembler);

    @Test
    public void shouldInvokeCreateInRepo() {
        sut.create(CREATE_CUSTOMER);
        verify(mockedRepo, times(1)).create(customerAssembler.assemble(CREATE_CUSTOMER));
    }

    @Test
    public void shouldInvokeGetByIdInRepo() {
        sut.getById(ID_FOR_GET);
        verify(mockedRepo, times(1)).getById(ID_FOR_GET);
    }

    @Test
    public void shouldInvokeUpdateInRepo() {
        sut.update(UPDATE_CUSTOMER);
        verify(mockedRepo, times(1)).update(customerAssembler.assemble(UPDATE_CUSTOMER));
    }

    @Test
    public void shouldInvokeDeleteInRepo() {
        sut.delete(ID_FOR_DELETE);
        verify(mockedRepo, times(1)).delete(ID_FOR_DELETE);
    }

    @Test
    public void shouldInvokeGetAllInRepo() {
        sut.getAll();
        verify(mockedRepo, times(1)).getAll();
    }
}
