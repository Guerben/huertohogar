package com.huertohogar.backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huertohogar.backend.dto.ProductoDto;
import com.huertohogar.backend.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*") // permitir solicitudes desde el origin especificado
public class ProductoController {

    private final ProductoService service;

    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @GetMapping
    public List<ProductoDto> listar() {
        return service.listarProductos();
    }

    @PostMapping
    public ProductoDto guardar(@RequestBody ProductoDto producto) {
        return service.guardarProducto(producto);
    }

    @GetMapping("/{id}")
    public ProductoDto obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

}
