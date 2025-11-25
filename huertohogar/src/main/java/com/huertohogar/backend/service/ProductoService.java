package com.huertohogar.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.huertohogar.backend.dto.ProductoDto;
import com.huertohogar.backend.model.Producto;
import com.huertohogar.backend.repository.ProductoRepository;

@Service
public class ProductoService {
    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<ProductoDto> listarProductos() {
        return repository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public ProductoDto guardarProducto(ProductoDto productoDto) {
        Producto producto = toEntity(productoDto);
        Producto saved = repository.save(producto);
        return toDto(saved);
    }

    public ProductoDto obtenerPorId(Long id) {
        return repository.findById(id).map(this::toDto).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    private ProductoDto toDto(Producto p) {
        if (p == null)
            return null;
        return new ProductoDto(
                p.getIdProducto(),
                p.getCodigo(),
                p.getNombre(),
                p.getDescripcion(),
                p.getPrecio(),
                p.getStock(),
                p.getOrigen(),
                p.getImagenUrl());
    }

    private Producto toEntity(ProductoDto dto) {
        Producto p = new Producto();
        if (dto.getId() != null) {
            p.setIdProducto(dto.getId());
        }
        p.setCodigo(dto.getCodigo());
        p.setNombre(dto.getNombre());
        p.setDescripcion(dto.getDescripcion());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setOrigen(dto.getOrigen());
        p.setImagenUrl(dto.getImagenUrl());
        return p;
    }

}
