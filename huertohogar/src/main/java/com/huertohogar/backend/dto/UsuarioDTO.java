package com.huertohogar.backend.dto;

import java.time.LocalDateTime;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private Long rolId;
    private String rolNombre;
    private LocalDateTime fechaCreacion;

    public UsuarioDTO() {
    }

    public UsuarioDTO(Long id, String nombre, String email, String password, String telefono, String direccion,
            Long rolId, String rolNombre, LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rolId = rolId;
        this.rolNombre = rolNombre;
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

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getRolId() {
        return this.rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public String getRolNombre() {
        return this.rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Compatibility alias for older code/tests that expect Spanish-style getter
    public Long getId_usuario() {
        return this.id;
    }

    public void setId_usuario(Long id) {
        this.id = id;
    }

}
