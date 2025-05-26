package spring.practice.elmenus_lite.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.practice.elmenus_lite.dto.customer.CustomerDto;
import spring.practice.elmenus_lite.handlerException.NotFoundCustomException;
import spring.practice.elmenus_lite.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> findAll() {
        List<CustomerDto> customerDto = customerService.findAll();
        return ResponseEntity.ok(customerDto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable("customerId") Long customerId) {
        CustomerDto customerDto = customerService.findById(customerId).orElseThrow(NotFoundCustomException::new);;
        return ResponseEntity.ok(customerDto);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> addCustomer(@RequestBody CustomerDto customer) {
        CustomerDto savedCustomer = this.customerService.add(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCustomer);
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<?> updateCustomerById(@PathVariable("customerId") Long customerId,
                                                @RequestBody CustomerDto customer) {
        CustomerDto customerDto = this.customerService.update(customerId, customer);
        return ResponseEntity.ok(customerDto);
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<?> delete(@PathVariable("customerId") Long customerId) {
        if (Boolean.FALSE.equals(this.customerService.delete(customerId))) {
            throw new NotFoundCustomException("can not delete this customer");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
