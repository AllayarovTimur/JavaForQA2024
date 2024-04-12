package ru.shop.Service;

import ru.shop.Exception.BadOrderCountException;
import ru.shop.Interface.Service;
import ru.shop.Model.Customer;
import ru.shop.Model.Order;
import ru.shop.Model.Product;
import ru.shop.Repository.OrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService implements Service<Order> {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository=orderRepository;
    }

    public void add(Customer customer, Product product, int count) {
        long amount = count * product.cost();
        Order order = new Order(UUID.randomUUID().toString(), customer.id(), product.id(), count, amount);

        orderRepository.save(order);
    }

    public void badOrderCount(Order order) throws BadOrderCountException {
        if (order.count() <= 0)
            throw new BadOrderCountException("Недостаточное количество");
    }

    public List<Order> findAll() {
        return (orderRepository.findAll());
    }

    public List<Order> findByCustomer(Customer customer) {
        List<Order> arrayByCustomer = new ArrayList<>();

        for (var i : orderRepository.findAll()) {
            if (i.customerId().equals(customer.id()))
                arrayByCustomer.add(i);
        }
        return arrayByCustomer;
    }

    public long getTotalCustomerAmount(Customer customer) {
        long sum = 0;
        for (var i : orderRepository.findAll()) {
            if (i.customerId().equals(customer.id()))
                sum += i.amount();
        }
        return sum;
    }
}
