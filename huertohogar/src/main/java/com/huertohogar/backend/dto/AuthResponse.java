package com.huertohogar.backend.dto;

public class AuthResponse {

    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String email;
    private String nombre;
    private String rolNombre;

    public AuthResponse() {}

    public AuthResponse(
        String token,
        Long id,
        String email,
        String nombre,
        String rolNombre) {
            this.token = token;
            this.id = id;
            this.email = email;
            this.nombre = nombre;
            this.rolNombre = rolNombre;
        }


    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRolNombre() {
        return this.rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

}
