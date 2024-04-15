package ru.shop;

import ru.shop.Exception.BadOrderCountException;
import ru.shop.Model.Customer;
import ru.shop.Model.Product;
import ru.shop.Repository.CustomerRepository;
import ru.shop.Repository.OrderRepository;
import ru.shop.Repository.ProductRepository;
import ru.shop.Service.CustomerService;
import ru.shop.Service.OrderService;
import ru.shop.Service.ProductService;
import ru.shop.Type.ProductType;

import java.util.UUID;


public class Main {
    public static void main(String[] args) {
        ProductRepository pR= new ProductRepository();
        CustomerRepository cR= new CustomerRepository();
        OrderRepository oR= new OrderRepository();

        ProductService pS = new ProductService(pR);
        CustomerService cS = new CustomerService(cR);
        OrderService oS = new OrderService(oR);

        Product chocolate = new Product(UUID.randomUUID().toString(), "Шоколадный батончик", 70, ProductType.GOOD);
        pS.save(chocolate);

        Product milk = new Product(UUID.randomUUID().toString(), "Молоко 1л", 80, ProductType.GOOD);
        pS.save(milk);

        Product loaf = new Product(UUID.randomUUID().toString(), "Батон", 31, ProductType.GOOD);
        pS.save(loaf);

        Product refrigeratorRepair = new Product(UUID.randomUUID().toString(), "Ремонт холодильника", 3000, ProductType.SERVICE);
        pS.save(refrigeratorRepair);

        Product internetSetup = new Product(UUID.randomUUID().toString(), "Настройка интернета", 500, ProductType.SERVICE);
        pS.save(internetSetup);

        Product shoeRepair = new Product(UUID.randomUUID().toString(), "Ремонт обуви", 400, ProductType.SERVICE);
        pS.save(shoeRepair);

        for(var i: pS.findAll()){
            if(i.id()==null || i.cost()<=0 || i.name()==null || i.productType()==null){
                System.out.println("Товар не соответствует");
                return ;
            }
        }

        Customer bulat = new Customer(UUID.randomUUID().toString(), "Таштимиров Булат", "89123456789", 19);
        cS.save(bulat);

        Customer aidar = new Customer(UUID.randomUUID().toString(), "Кашапов Айдар", "89987654321", 19);
        cS.save(aidar);

        Customer arslan = new Customer(UUID.randomUUID().toString(), "Валитов Арслан", "89981237645", 19);
        cS.save(arslan);

        for (var i: cS.findAll()){
            if(i.id()==null || i.name()==null || i.phone().length()!=11  || i.age()<=0){
                System.out.println("Покупатель не соответствует");
                return ;
            }
        }

        oS.add(aidar, chocolate, 1);
        oS.add(bulat , refrigeratorRepair, 1);
        oS.add(arslan, shoeRepair, 50);
        oS.add(arslan, milk, 4);

        for(var i: oS.findAll()){
            if(i.id()==null || i.productId()==null || i.customerId()==null){
                System.out.println("Заказ не соответствует");
                return ;
            }
        }

        for(var i: oS.findAll()){
            try{
                oS.badOrderCount(i);
            } catch (BadOrderCountException e){
                System.out.println("count cannot be less than or equal to 0");
                return;
            }
        }

        System.out.println("Количество покупателей: " + cS.findAll().size());
        System.out.println("Количество заказов: " + oS.findAll().size());
        System.out.println("Количество заказовиз категории \"Товары\": " + pS.findByProductType(ProductType.GOOD).size());
        System.out.println("Количество заказов из категории \"Услуги\": " + pS.findByProductType(ProductType.SERVICE).size());

        for(var i: cS.findAll()){
            System.out.println("Количество заказов " + i.name() + ": " + oS.findByCustomer(i).size());
        }

        long cnt=0;
        for(var i: cS.findAll()){
            for(var j: oS.findByCustomer(i)){
                cnt+=j.amount();
            }
            System.out.println("К оплате для " + i.name() + ": " + cnt);
            cnt =0;
        }
    }
}