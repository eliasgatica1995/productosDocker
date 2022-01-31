package com.db.productosMongo.service;


import com.db.productosMongo.model.Producto;
import com.db.productosMongo.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository repository;

    @Override
    public Producto createProduct(Producto producto) {
        return repository.save(producto);
    }

    @Override
    public Producto getProductById(Long id) {
        return repository.findById(id).get();
    }

    @Override
    public Producto updateProductById(Producto producto, Long id) {
        producto.setId(id);
        return repository.save(producto);
    }

    @Override
    public void delete(Long id) {

        repository.deleteById(id);
    }

    @Override
    public Producto findByName(String name) {
        return repository.findByName(name);
    }


    @Override
    public List<Producto> findByStockGreaterThan(int age) {
        return repository.findByStockGreaterThan(age);
    }

    @Override
    public List<Producto> findAll() {
        List<Producto> userList =  new ArrayList<>();
        repository.findAll().forEach(userList::add);
        return userList;
    }
}
