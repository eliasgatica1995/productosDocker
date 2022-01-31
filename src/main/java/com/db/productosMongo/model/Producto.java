package com.db.productosMongo.model;


import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Producto")
@Builder
public class Producto {

    @Id
    private Long id;
    private String name;
    private int stock;

    public Producto() {}

    public Producto(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }
    public Producto(Long id,String name, int stock) {
        this.id=id;
        this.name = name;
        this.stock = stock;
    }
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
