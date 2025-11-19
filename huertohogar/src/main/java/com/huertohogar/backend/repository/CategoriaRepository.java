package com.huertohogar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.huertohogar.backend.model.CategoriaModel;
import java.util.List;
import java.util.Optional;


@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaModel, Long> {

    Optional<CategoriaModel> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    List<CategoriaModel> findAllByOrderByNombreAsc();

    List<CategoriaModel> findByActivoOrderByNombreAsc(Boolean activBoolean);

    @Query("SELECT c FROM CategoriaModel c WHERE LOWER(c.nombre) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<CategoriaModel> buscarPorNombre(String texto);

}
