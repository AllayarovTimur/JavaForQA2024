package ru.shop.Interface;

import java.util.List;

public interface Repository <OBJECT>{
    void save(OBJECT t);
    List<OBJECT> findAll();
}
