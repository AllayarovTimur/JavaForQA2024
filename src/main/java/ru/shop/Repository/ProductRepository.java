package ru.shop.Repository;

import ru.shop.Interface.Repository;
import ru.shop.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository implements Repository<Product> {
    List<Product> arrayList = new ArrayList<>();

    public void save(Product product) {
        arrayList.add(product);
    }

    public List<Product> findAll() {
        List<Product> arrayList1 = new ArrayList<>();
        arrayList1.addAll(arrayList);

        return arrayList1;
    }
}
