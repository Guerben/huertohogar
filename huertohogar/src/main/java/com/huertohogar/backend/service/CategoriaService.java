package com.huertohogar.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huertohogar.backend.dto.CategoriaDto;
import com.huertohogar.backend.model.CategoriaModel;
import com.huertohogar.backend.repository.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public CategoriaDto crearCategoria(CategoriaDto categoriaDto) {

        if (categoriaRepository.existsByNombre(categoriaDto.getNombre())) {
            throw new RuntimeException("La categoria '" + categoriaDto.getNombre() + "' ya existe");
        }

        CategoriaModel categoria = convertirDTOToModel(categoriaDto);

        CategoriaModel categoriaGuardado = categoriaRepository.save(categoria);

        return convertirModelToDTO(categoriaGuardado);
    }

    public List<CategoriaDto> obtenerCategorias() {

        return categoriaRepository.findAllByOrderByNombreAsc()
                .stream()
                .map(this::convertirModelToDTO)
                .collect(Collectors.toList());
    }

    public List<CategoriaDto> obtenerCategoriasActivos() {
        return categoriaRepository.findByActivoOrderByNombreAsc(true)
                .stream()
                .map(this::convertirModelToDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDto obtenerCategoriaPorId(Long id) {
        CategoriaModel categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id '" + id + "' no encontrada"));
        return convertirModelToDTO(categoria);
    }

    public CategoriaDto obtenerCategoriaPorNombre(String nombre) {
        CategoriaModel categoria = categoriaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Categoria con nombre '" + nombre + "' no encontrada"));
        return convertirModelToDTO(categoria);
    }

    public List<CategoriaDto> buscarCategorias(String texto) {
        return categoriaRepository.buscarPorNombre(texto)
                .stream()
                .map(this::convertirModelToDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDto actualizarCategoria(Long id, CategoriaDto categoriaDto) {
        CategoriaModel categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id '" + id + "' no encontrada"));

        categoriaExistente.setNombre(categoriaDto.getNombre());
        categoriaExistente.setDescripcion(categoriaDto.getDescripcion());
        categoriaExistente.setImagenurl(categoriaDto.getImagenurl());
        categoriaExistente.setActivo(categoriaDto.getActivo());

        CategoriaModel categoriaActualizado = categoriaRepository.save(categoriaExistente);

        return convertirModelToDTO(categoriaActualizado);
    }

    public CategoriaDto cambiarEstadoCategoria(Long id, boolean activo) {
        CategoriaModel categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria con id '" + id + "' no encontrada"));

        categoria.setActivo(activo);

        CategoriaModel categoriaActualizado = categoriaRepository.save(categoria);

        return convertirModelToDTO(categoriaActualizado);

    }

    public void eliminarCategoria(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria con id '" + id + "' no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    private CategoriaModel convertirDTOToModel(CategoriaDto categoriaDto) {
        return new CategoriaModel(
                categoriaDto.getId(),
                categoriaDto.getNombre(),
                categoriaDto.getDescripcion(),
                categoriaDto.getImagenurl(),
                categoriaDto.getActivo(),
                categoriaDto.getFechaCreacion());
    }

    private CategoriaDto convertirModelToDTO(CategoriaModel categoriaModel) {
        return new CategoriaDto(
                categoriaModel.getId(),
                categoriaModel.getNombre(),
                categoriaModel.getDescripcion(),
                categoriaModel.getImagenurl(),
                categoriaModel.getActivo(),
                categoriaModel.getFechaCreacion());
    }

}
