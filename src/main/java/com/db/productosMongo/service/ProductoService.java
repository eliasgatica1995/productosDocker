package com.db.productosMongo.service;

import com.db.productosMongo.model.Producto;

import java.util.List;

public interface ProductoService {
    Producto createProduct(Producto producto);
    Producto getProductById(Long id);
    Producto updateProductById(Producto producto, Long id);
    void delete(Long id);
    Producto findByName(String name);
    List<Producto> findByStockGreaterThan(int age);

    List<Producto> findAll();

}
