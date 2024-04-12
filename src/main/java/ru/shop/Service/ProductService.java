package ru.shop.Service;

import ru.shop.Interface.Service;
import ru.shop.Model.Product;
import ru.shop.Repository.ProductRepository;
import ru.shop.Type.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductService implements Service<Product> {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public List<Product> findAll() {
        return (productRepository.findAll());
    }

    public List<Product> findByProductType(ProductType productType) {
        List<Product> arrayByProductType = new ArrayList<>();

        for (var i : productRepository.findAll()) {
            if (i.productType()==productType)
                arrayByProductType.add(i);
        }
        return arrayByProductType;
    }
}
