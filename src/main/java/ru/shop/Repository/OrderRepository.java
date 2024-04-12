package ru.shop.Repository;

import ru.shop.Interface.Repository;
import ru.shop.Model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements Repository<Order> {
    List<Order> arrayList = new ArrayList<>();

    public void save(Order order) {
        arrayList.add(order);
    }

    public List<Order> findAll() {
        List<Order> arrayList1 = new ArrayList<>();
        arrayList1.addAll(arrayList);

        return arrayList1;
    }
}
