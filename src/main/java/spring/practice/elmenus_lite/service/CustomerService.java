package spring.practice.elmenus_lite.service;

import spring.practice.elmenus_lite.dto.CustomerResponseDto;
import spring.practice.elmenus_lite.entity.Customer;

import java.util.List;

public interface CustomerService {
    CustomerResponseDto createCustomer(CustomerResponseDto newCustomer);
    CustomerResponseDto updateCustomer(Long id , CustomerResponseDto customer);
    void deleteCustomer(Long id);
    CustomerResponseDto getCustomer(Long id);
    List<CustomerResponseDto> getAllCustomers();
}
