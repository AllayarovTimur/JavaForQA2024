package ru.shop.Model;

import ru.shop.Type.ProductType;

public record Product(String id, String name, long cost, ProductType productType) {

}
