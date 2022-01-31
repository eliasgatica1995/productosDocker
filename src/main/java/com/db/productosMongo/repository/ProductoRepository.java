package com.db.productosMongo.repository;


import com.db.productosMongo.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface ProductoRepository extends MongoRepository<Producto, Long> {

    Producto findByName(String name);
    List<Producto> findByStockGreaterThan(int age);
}



