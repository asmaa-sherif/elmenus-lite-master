package spring.practice.elmenus_lite.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.practice.elmenus_lite.dto.customer.CustomerDto;
import spring.practice.elmenus_lite.entity.Customer;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.mapper.CustomerMapper;
import spring.practice.elmenus_lite.repository.CustomerRepository;
import spring.practice.elmenus_lite.service.CustomerService;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public List<CustomerDto> findAll() {
        return this.customerMapper.customerListToCustomerDtoList(this.customerRepository.findAll());
    }

    @Override
    public Optional<CustomerDto> findById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Customer ID must be greater than 0 and not null");
        }
        return Optional.ofNullable(this.customerMapper.customerToCustomerDto(this.customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundCustomException("Customer with ID " + id + " not found"))));
    }

    @Override
    public CustomerDto add(CustomerDto newCustomer) {
        if (newCustomer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        Customer customer = new Customer();
        if (newCustomer.getGender() != null) {
            customer.setGender(newCustomer.getGender());
        }
        if (newCustomer.getPhone() != null) {
            customer.setPhone(newCustomer.getPhone());
        }
        return customerMapper.customerToCustomerDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDto update(Long customerId, CustomerDto newCustomer) {
        if (newCustomer == null) {
            throw new IllegalArgumentException("Customer data cannot be null");
        }

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NotFoundCustomException("Item not exist to make update"));

        if (newCustomer.getGender() != null) {
            customer.setGender(newCustomer.getGender());
        }
        if (newCustomer.getPhone() != null) {
            customer.setPhone(newCustomer.getPhone());
        }

        Customer updatedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDto(updatedCustomer);
    }

    @Override
    public Boolean delete(Long customerId) {
        if (this.customerRepository.existsById(customerId)) {
            this.customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }
}
