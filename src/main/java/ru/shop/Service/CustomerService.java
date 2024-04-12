package ru.shop.Service;

import ru.shop.Interface.Service;
import ru.shop.Repository.CustomerRepository;
import ru.shop.Model.Customer;

import java.util.List;

public class CustomerService implements Service<Customer> {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public List<Customer> findAll() {
        return (customerRepository.findAll());
    }
}
