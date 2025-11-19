package com.huertohogar.backend.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import com.huertohogar.backend.repository.RolRepository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class RolModel implements RolRepository {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idRol;

    @Column(nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(length = 255)
    private String descripcion;

    public RolModel() {
    }

    public RolModel(Long idRol, String nombre, String descripcion) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Long getId() {
        return this.idRol;
    }

    public void setId(Long id) {
        this.idRol = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public Optional<RolModel> findByNombre(String nombre) {
        return Optional.empty();
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return false;
    }

    @Override
    public void flush() {

    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel> S saveAndFlush(S entity) {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel> List<S> saveAllAndFlush(@SuppressWarnings("null") Iterable<S> entities) {
        return List.of();
    }

    @SuppressWarnings("null")
    @Override
    public void deleteAllInBatch(Iterable<RolModel> entities) {

    }

    @SuppressWarnings("null")
    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @SuppressWarnings("null")
    @Override
    public RolModel getOne(Long aLong) {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public RolModel getById(Long aLong) {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public RolModel getReferenceById(Long aLong) {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel> Optional<S> findOne(@SuppressWarnings("null") Example<S> example) {
        return Optional.empty();
    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel> List<S> findAll(@SuppressWarnings("null") Example<S> example) {
        return List.of();
    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel> List<S> findAll(@SuppressWarnings("null") Example<S> example, @SuppressWarnings("null") Sort sort) {
        return List.of();
    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel> Page<S> findAll(@SuppressWarnings("null") Example<S> example, @SuppressWarnings("null") Pageable pageable) {
        return null;
    }

    @Override
    public <S extends RolModel> long count(@SuppressWarnings("null") Example<S> example) {
        return 0;
    }

    @Override
    public <S extends RolModel> boolean exists(@SuppressWarnings("null") Example<S> example) {
        return false;
    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel, R> R findBy(@SuppressWarnings("null") Example<S> example, @SuppressWarnings("null") Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel> S save(@SuppressWarnings("null") S entity) {
        return null;
    }

    @SuppressWarnings("null")
    @Override
    public <S extends RolModel> List<S> saveAll(@SuppressWarnings("null") Iterable<S> entities) {
        return List.of();
    }

    @SuppressWarnings("null")
    @Override
    public Optional<RolModel> findById(@SuppressWarnings("null") Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(@SuppressWarnings("null") Long aLong) {
        return false;
    }

    @SuppressWarnings("null")
    @Override
    public List<RolModel> findAll() {
        return List.of();
    }

    @SuppressWarnings("null")
    @Override
    public List<RolModel> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(@SuppressWarnings("null") Long aLong) {

    }

    @SuppressWarnings("null")
    @Override
    public void delete(RolModel entity) {

    }

    @SuppressWarnings("null")
    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @SuppressWarnings("null")
    @Override
    public void deleteAll(Iterable<? extends RolModel> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @SuppressWarnings("null")
    @Override
    public List<RolModel> findAll(Sort sort) {
        return List.of();
    }

    @SuppressWarnings("null")
    @Override
    public Page<RolModel> findAll(Pageable pageable) {
        return null;
    }
}
