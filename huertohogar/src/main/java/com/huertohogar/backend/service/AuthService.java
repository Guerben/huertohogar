package com.huertohogar.backend.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.huertohogar.backend.dto.AuthResponse;
import com.huertohogar.backend.dto.LoginRequest;
import com.huertohogar.backend.dto.RegisterRequest;
import com.huertohogar.backend.dto.UsuarioDTO;
import com.huertohogar.backend.model.RolModel;
import com.huertohogar.backend.model.UsuarioModel;
import com.huertohogar.backend.repository.RolRepository;
import com.huertohogar.backend.repository.UsuarioRepository;
import com.huertohogar.backend.util.JwtUtil;

@Service
public class AuthService {

        @Autowired
        private UsuarioRepository usuarioRepository;

        @Autowired
        private RolRepository rolRepository;

        @Autowired
        private JwtUtil jwtUtil;

        @Autowired
        private PasswordEncoder passwordEncoder;

        public AuthResponse registrar(RegisterRequest request) {

                if (usuarioRepository.existsByEmail(request.getEmail())) {
                        throw new RuntimeException("El email ya esta registrado");
                }

                Long rolId = request.getRolId();
                if (rolId == null) {

                        RolModel rolCliente = rolRepository.findByNombre("CLIENTE")
                                        .orElseThrow(() -> new RuntimeException("Rol Cliente no encontrado"));
                        rolId = rolCliente.getId();
                }

                UsuarioModel nuevoUsuario = new UsuarioModel();
                nuevoUsuario.setNombre(request.getNombre());
                nuevoUsuario.setEmail(request.getEmail());

                nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));

                nuevoUsuario.setRolId(rolId);
                nuevoUsuario.setTelefono(request.getTelefono());
                nuevoUsuario.setDireccion(request.getDireccion());
                nuevoUsuario.setFechaRegistro(LocalDateTime.now());

                UsuarioModel usuarioGuardado = usuarioRepository.save(nuevoUsuario);

                RolModel rol = rolRepository.findById(rolId)
                                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

                String token = jwtUtil.generarToken(
                                usuarioGuardado.getEmail(),
                                usuarioGuardado.getId_usuario(),
                                rol.getNombre());

                return new AuthResponse(
                                token,
                                usuarioGuardado.getId_usuario(),
                                usuarioGuardado.getEmail(),
                                usuarioGuardado.getNombre(),
                                rol.getNombre());

        }

        public AuthResponse login(LoginRequest request) {

                UsuarioModel usuario = usuarioRepository.findByEmail(request.getEmail())
                                .orElseThrow();

                if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                        throw new RuntimeException("Credenciales inválidas");
                }

                RolModel rol = rolRepository.findById(usuario.getRolId())
                                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

                String token = jwtUtil.generarToken(
                                usuario.getEmail(),
                                usuario.getId_usuario(),
                                rol.getNombre());

                return new AuthResponse(
                                token,
                                usuario.getId_usuario(),
                                usuario.getEmail(),
                                usuario.getNombre(),
                                rol.getNombre());
        }

        // Authenticate user credentials and return a UsuarioDTO (used for session-based
        // login)
        public UsuarioDTO authenticateUser(LoginRequest request) {
                UsuarioModel usuario = usuarioRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                if (!passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
                        throw new RuntimeException("Credenciales inválidas");
                }

                RolModel rol = rolRepository.findById(usuario.getRolId())
                                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

                String rolNombre = rol != null ? rol.getNombre() : null;

                return new UsuarioDTO(
                                usuario.getId_usuario(),
                                usuario.getNombre(),
                                usuario.getEmail(),
                                null,
                                usuario.getTelefono(),
                                usuario.getDireccion(),
                                usuario.getRolId(),
                                rolNombre,
                                usuario.getFechaRegistro());
        }

        // Handle OAuth2 login: find existing user by email or create one, then return
        // UsuarioDTO
        public UsuarioDTO processOAuthPostLogin(java.util.Map<String, Object> attributes, String provider) {
                // Try to extract standard attributes
                String email = (String) attributes.get("email");
                String name = (String) attributes.getOrDefault("name", attributes.get("given_name"));

                if (email == null) {
                        throw new RuntimeException("No se encontró email en atributos de OAuth2");
                }

                UsuarioModel usuario = usuarioRepository.findByEmail(email).orElse(null);
                if (usuario == null) {
                        // Crear nuevo usuario rápido
                        UsuarioModel nuevo = new UsuarioModel();
                        nuevo.setEmail(email);
                        nuevo.setNombre(name != null ? name : email);
                        // Generar password aleatoria (no se usa para login social)
                        nuevo.setPassword(passwordEncoder.encode(java.util.UUID.randomUUID().toString()));

                        // Asignar rol CLIENTE por defecto
                        RolModel rolCliente = rolRepository.findByNombre("CLIENTE").orElse(null);
                        if (rolCliente != null) {
                                nuevo.setRolId(rolCliente.getId());
                        }
                        nuevo.setFechaRegistro(LocalDateTime.now());

                        usuario = usuarioRepository.save(nuevo);
                }

                RolModel rol = rolRepository.findById(usuario.getRolId()).orElse(null);
                String rolNombre = rol != null ? rol.getNombre() : null;

                return new UsuarioDTO(
                                usuario.getId_usuario(),
                                usuario.getNombre(),
                                usuario.getEmail(),
                                null,
                                usuario.getTelefono(),
                                usuario.getDireccion(),
                                usuario.getRolId(),
                                rolNombre,
                                usuario.getFechaRegistro());
        }

        public boolean validarToken(String token) {
                return jwtUtil.validarToken(token);
        }

        public UsuarioDTO obtenerUsuarioDelToken(String token) {
                // Extraer email del token
                String email = jwtUtil.obtenerEmailDelToken(token);

                UsuarioModel usuario = usuarioRepository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

                RolModel rol = rolRepository.findById(usuario.getRolId())
                                .orElse(null);

                String rolNombre = rol != null ? rol.getNombre() : null;

                return new UsuarioDTO(
                                usuario.getId_usuario(),
                                usuario.getNombre(),
                                usuario.getEmail(),
                                null, // No enviar password
                                usuario.getTelefono(),
                                usuario.getDireccion(),
                                usuario.getRolId(),
                                rolNombre,
                                usuario.getFechaRegistro());
        }

}
