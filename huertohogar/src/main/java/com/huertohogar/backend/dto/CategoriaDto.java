package com.huertohogar.backend.dto;

import java.time.LocalDateTime;

public class CategoriaDto {

    private Long id;
    private String nombre;
    private String descripcion;
    private String imagenurl;
    private Boolean activo;
    private LocalDateTime fechaCreacion;

    
    public CategoriaDto() {}

    public CategoriaDto(Long id, String nombre, String descripcion, String imagenurl, Boolean activo, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagenurl = imagenurl;
        this.activo = activo;
        this.fechaCreacion = fechaCreacion;
    
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getImagenurl() {
        return this.imagenurl;
    }

    public void setImagenurl(String imagenurl) {
        this.imagenurl = imagenurl;
    }

    public Boolean isActivo() {
        return this.activo;
    }

    public Boolean getActivo() {
        return this.activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }


}
