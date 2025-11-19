package com.huertohogar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.huertohogar.backend.model.UsuarioModel;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioModel, Long> {

    Optional<UsuarioModel> findByEmail(String email);

    boolean existsByEmail(String email);

    List<UsuarioModel> findByRolId(Long rolId);

    @Query("SELECT u FROM UsuarioModel u LEFT JOIN FETCH u.rol WHERE u.id_usuario = :id")
    Optional<UsuarioModel> findByIdWithRol(Long id);

    @Query("SELECT u FROM UsuarioModel u LEFT JOIN FETCH u.rol")
    List<UsuarioModel> findAllWithRol();

}
