package com.huertohogar.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.huertohogar.backend.model.RolModel;

@Repository
public interface RolRepository extends JpaRepository<RolModel, Long> {

    Optional<RolModel> findByNombre(String nombre);

    boolean existsByNombre(String nombre);

}
