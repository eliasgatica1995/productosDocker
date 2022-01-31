package com.db.productosMongo.controller;

import com.db.productosMongo.model.Producto;
import com.db.productosMongo.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventario")
public class ProductoController {

    @Autowired
    ProductoService service;

    @PostMapping("/producto")
    public Producto createUser(@RequestBody Producto producto) {
        return service.createProduct(producto);
    }

    @GetMapping("/producto/all")
    public List<Producto> findUsers() {
        return service.findAll();
    }

    @GetMapping("/producto")
    public Producto findByName(@RequestParam String name) {
        return service.findByName(name);
    }

    @PutMapping("/producto/{id}")
    public Producto updateProductById(@PathVariable Long id, @RequestBody  Producto producto) {
        return service.updateProductById(producto, id);
    }
    @DeleteMapping("/producto/{id}")
    public String deleteProducto(@PathVariable Long id) {
        service.delete(id);
        return "Borrado de producto";
    }
}