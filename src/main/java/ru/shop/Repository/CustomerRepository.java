package ru.shop.Repository;

import ru.shop.Interface.Repository;
import ru.shop.Model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements Repository<Customer> {
    List<Customer> arrayList = new ArrayList<>();

    public void save(Customer customer) {
        arrayList.add(customer);
    }

    public List<Customer> findAll() {
        List<Customer> arrayList1 = new ArrayList<>();
        arrayList1.addAll(arrayList);

        return arrayList1;
    }
}
