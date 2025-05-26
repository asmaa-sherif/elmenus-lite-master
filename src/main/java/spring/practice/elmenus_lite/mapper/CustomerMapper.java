package spring.practice.elmenus_lite.mapper;

import org.mapstruct.Mapper;
import spring.practice.elmenus_lite.dto.customer.CustomerDto;
import spring.practice.elmenus_lite.entity.Customer;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto dto);

    CustomerDto customerToCustomerDto(Customer customer);

    List<Customer> customerDtoListToCustomerList(List<CustomerDto> dto);

    List<CustomerDto> customerListToCustomerDtoList(List<Customer> customer);
}
