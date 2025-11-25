package com.huertohogar.backend.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuarios")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id_usuario;

    @Column(nullable = false, length = 100)
    private String nombre; // nombre completo del usuario

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(length = 200)
    private String direccion;

    @Column(length = 20)
    private String telefono;

    @Column(name = "rol_id")
    private Long rolId;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", insertable = false, updatable = false)
    private RolModel rol;

    public UsuarioModel() {
        this.fechaRegistro = LocalDateTime.now();
    }

    public UsuarioModel(String nombre, String email, String password, String direccion, String telefono, Long rolId) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.direccion = direccion;
        this.telefono = telefono;
        this.rolId = rolId;
        this.fechaRegistro = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId_usuario() {
        return this.id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
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

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Long getRolId() {
        return this.rolId;
    }

    public void setRolId(Long rolId) {
        this.rolId = rolId;
    }

    public LocalDateTime getFechaRegistro() {
        return this.fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public RolModel getRol() {
        return this.rol;
    }

    public void setRol(RolModel rol) {
        this.rol = rol;
    }

}
