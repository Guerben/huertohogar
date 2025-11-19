package com.huertohogar.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huertohogar.backend.dto.CategoriaDto;
import com.huertohogar.backend.service.CategoriaService;

@RestController
@RequestMapping("/api/categorias")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;



    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("path")
    public ResponseEntity<?> crearCategoria(@RequestBody CategoriaDto categoria) {
        try {
            CategoriaDto nuevaCategoria = categoriaService.crearCategoria(categoria);
            return new ResponseEntity<>(nuevaCategoria, HttpStatus.CREATED);

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("path")
    public ResponseEntity<List<CategoriaDto>> obtenerCategorias() {
        List<CategoriaDto> categorias = categoriaService.obtenerCategorias();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("path/activos")
    public ResponseEntity<List<CategoriaDto>> obtenerCategoriasActivos() {
        List<CategoriaDto> categorias = categoriaService.obtenerCategoriasActivos();
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @GetMapping("path/id/{id}")
    public ResponseEntity<CategoriaDto> obtenerCategoriaPorId(@PathVariable Long id) {
        CategoriaDto categoria = categoriaService.obtenerCategoriaPorId(id);
        if (categoria != null) {
            return new ResponseEntity<>(categoria, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("path/buscar/{texto}")
    public ResponseEntity<List<CategoriaDto>> buscarCategorias(@PathVariable String texto) {
        List<CategoriaDto> categorias = categoriaService.buscarCategorias(texto);
        return new ResponseEntity<>(categorias, HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable Long id, @RequestBody CategoriaDto categoriaDto) {
        try {
            CategoriaDto categoria = categoriaService.actualizarCategoria(id, categoriaDto);
            return new ResponseEntity<>(categoria, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PatchMapping("/cambiar-estado/{id}")
    public ResponseEntity<?> cambiarEstadoCategoria(@PathVariable Long id) {
        try {
            CategoriaDto categoria = categoriaService.cambiarEstadoCategoria(id, false);
            return new ResponseEntity<>(categoria, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable Long id) {
        try {
            categoriaService.eliminarCategoria(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

}
