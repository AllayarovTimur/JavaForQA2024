package ru.shop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import static java.util.Arrays.asList;


enum ProductType{
    GOOD,
    SERVICE;
}

record Product (String id, String name, long cost, ProductType productType) {

}

record Customer(String id, String name, String phone, long age) {

}

record Order(String id, String customerId, String productId, long count, long amount) {

}

class ProductRepository{
    List<Product> arrayList = new ArrayList<>();

    public void save(Product product){
        arrayList.add(product);
    }

    public List<Product> findAll(){
        List<Product> arrayList1 = new ArrayList<>();
        arrayList1.addAll(arrayList);

        return arrayList1;
    }
}

class OrderRepository{
    List<Order> arrayList = new ArrayList<>();

    public void save(Order order){
        arrayList.add(order);
    }

    public List<Order> findAll(){
        List<Order> arrayList1 = new ArrayList<>();
        arrayList1.addAll(arrayList);

        return arrayList1;
    }
}

class CustomerRepository{
    List<Customer> arrayList = new ArrayList<>();

    public void save(Customer customer){
        arrayList.add(customer);
    }

    public List<Customer> findAll(){
        List<Customer> arrayList1 = new ArrayList<>();
        arrayList1.addAll(arrayList);

        return arrayList1;
    }
}

class ProductService  {
    private final ProductRepository productRepository = new ProductRepository();

    public void save(Product product){
        productRepository.save(product);
    }

    public List<Product> findAll(){
        return(productRepository.findAll());
    }

    public List<Product> findByProductType(ProductType productType)
    {
        List<Product> arrayByProductType = new ArrayList<>();

        for(var i : productRepository.findAll()){
            if (i.productType().equals(productType))
                arrayByProductType.add(i);
        }
        return arrayByProductType;
    }
}

class CustomerService{
    private final CustomerRepository customerRepository = new CustomerRepository();

    public void save(Customer customer){
        customerRepository.save(customer);
    }

    public List<Customer> findAll(){
        return(customerRepository.findAll());
    }
}

class OrderService {
    private final OrderRepository orderRepository = new OrderRepository();

    public void add(Customer customer, Product product, int count){
        long amount = count*product.cost();
        Order order  = new Order(UUID.randomUUID().toString(), customer.id(), product.id(), count, amount);

        orderRepository.save(order);
    }

    public void badOrderCount (Order order) throws BadOrderCountException{
        if (order.count()<=0)
            throw new BadOrderCountException("Недостаточное количество");
    }

    public List<Order> findAll(){
        return(orderRepository.findAll());
    }

    public List<Order> findByCustomer(Customer customer){
        List<Order> arrayByCustomer = new ArrayList<>();

        for(var i : orderRepository.findAll()){
            if (i.customerId().equals(customer.id()))
                arrayByCustomer.add(i);
        }
        return arrayByCustomer;
    }

    public long getTotalCustomerAmount(Customer customer){
        long sum=0;
        for(var i: orderRepository.findAll()){
            if(i.customerId().equals(customer.id()))
             sum+=i.amount();
        }
        return sum;
    }
}

class BadOrderCountException extends Exception{
    public BadOrderCountException (String message){
        super(message);
    }
}


public class Main {
    public static void main(String[] args) {
        ProductService pS = new ProductService();
        CustomerService cS = new CustomerService();
        OrderService oS = new OrderService();

        ProductRepository pR= new ProductRepository();
        CustomerRepository cR= new CustomerRepository();
        OrderRepository oR= new OrderRepository();

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


        Customer bulat = new Customer(UUID.randomUUID().toString(), "Таштимиров Булат", "89123456789", 19);
        cS.save(bulat);

        Customer aidar = new Customer(UUID.randomUUID().toString(), "Кашапов Айдар", "89987654321", 19);
        cS.save(aidar);

        Customer arslan = new Customer(UUID.randomUUID().toString(), "Валитов Арслан", "89981237645", 19);
        cS.save(arslan);


        oS.add(aidar, chocolate, 1);
        oS.add(bulat, refrigeratorRepair, 1);
        oS.add(arslan, shoeRepair, 50);
        oS.add(arslan, milk, 4);

        for(var i: oS.findAll()){
            try{
                oS.badOrderCount(i);
            } catch (BadOrderCountException e){
                System.out.println("count cannot be less than or equal to 0");
            }
        }

        int cnt=0;
        for(var i: cS.findAll()){
            cnt++;
        }
        System.out.println("Количество покупателей: " + cnt);
        cnt =0;

        for(var i: oS.findAll()){
            cnt++;
        }
        System.out.println("Количество заказов: " + cnt);

        int cnt1=0;

        for(var i: oS.findAll()){
            for(var j: pS.findByProductType(ProductType.GOOD)){
                if(i.productId().equals(j.id()))
                    cnt1++;
            }
        }
        System.out.println("Количество заказов из категории \"Товары\": " + cnt1);
        System.out.println("Количество заказов из категории \"Услуги\": " + (cnt-cnt1));
        cnt =0;
        cnt1=0;

        for(var i: cS.findAll()){
            for(var j: oS.findByCustomer(i)){
                cnt++;
            }
            System.out.println("Количество заказов " + i.name() + ": " + cnt);
            cnt =0;
        }

        for(var i: cS.findAll()){
            for(var j: oS.findByCustomer(i)){
                cnt+=j.amount();
            }
            System.out.println("К оплате для " + i.name() + ": " + cnt);
            cnt =0;
        }
    }
}