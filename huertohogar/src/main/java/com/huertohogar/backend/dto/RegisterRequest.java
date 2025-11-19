package com.huertohogar.backend.dto;

public class RegisterRequest {

    private String nombre;
    private String email;
    private String password;
    private String telefono;
    private String direccion;
    private Long rolId;

    public RegisterRequest() {}

    public RegisterRequest(String nombre, String email, String password, String telefono, String direccion, Long rolId) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rolId = rolId;
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

}
