package spring.practice.elmenus_lite.service.customer;

import spring.practice.elmenus_lite.dto.customer.CustomerDto;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

    List<CustomerDto> findAll();

    Optional<CustomerDto> findById(Long id);

    CustomerDto add(CustomerDto newCustomer);

    CustomerDto update(Long customerId, CustomerDto newCustomer);

    Boolean delete(Long customerId);

}
