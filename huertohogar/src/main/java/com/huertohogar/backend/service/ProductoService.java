package com.huertohogar.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.huertohogar.backend.model.Producto;
import com.huertohogar.backend.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository){
        this.repository = repository;
    }

    public List<Producto> listarProductos(){
        return repository.findAll();
    }

    public Producto guardarProducto(Producto producto){
        return repository.save(producto);
        
    }

    public Producto obtenerPorId(Long id){
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id){
        repository.deleteById(id);
    }

}
