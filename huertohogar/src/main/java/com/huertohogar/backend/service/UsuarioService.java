package com.huertohogar.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huertohogar.backend.dto.UsuarioDTO;
import com.huertohogar.backend.model.UsuarioModel;
import com.huertohogar.backend.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public UsuarioDTO crearUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioRepository.existsByEmail(usuarioDTO.getEmail())) {
            throw new RuntimeException("El email ya está Registrado");

        }

        UsuarioModel usuario = convertDTOtoModel(usuarioDTO);
        usuario.setFechaRegistro(LocalDateTime.now());

        UsuarioModel usuarioGuardado = usuarioRepository.save(usuario);
        return convertModelToDTO(usuarioGuardado);
    }

    public List<UsuarioDTO> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAllWithRol().stream().map(this::convertModelToDTO).collect(Collectors.toList());
    }

    public UsuarioDTO obtenerUsuarioPorId(Long id) {
        UsuarioModel usuario = usuarioRepository.findByIdWithRol(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return convertModelToDTO(usuario);
    }

    public UsuarioDTO obtenerUsuarioPorEmail(String email) {
        UsuarioModel usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
        return convertModelToDTO(usuario);
    }

    public List<UsuarioDTO> obtenerUsuarioPorRol(Long rolId) {
        return usuarioRepository.findByRolId(rolId).stream().map(this::convertModelToDTO).collect(Collectors.toList());
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        UsuarioModel usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setEmail(usuarioDTO.getEmail());

        if (usuarioDTO.getPassword() != null && !usuarioDTO.getPassword().isEmpty()) {
            usuarioExistente.setPassword(usuarioDTO.getPassword());
        }

        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setDireccion(usuarioDTO.getDireccion());
        usuarioExistente.setRolId(usuarioDTO.getRolId());

        UsuarioModel usuarioActualizado = usuarioRepository.save(usuarioExistente);
        return convertModelToDTO(usuarioActualizado);
    }

    public void eliminarUsuario(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    private UsuarioModel convertDTOtoModel(UsuarioDTO dto) {
        UsuarioModel usuario = new UsuarioModel(
                dto.getNombre(),
                dto.getEmail(),
                dto.getPassword(),
                dto.getDireccion(),
                dto.getTelefono(),
                dto.getRolId());
        if (dto.getId() != null) {
            usuario.setId_usuario(dto.getId());
        }
        if (dto.getFechaCreacion() != null) {
            usuario.setFechaRegistro(dto.getFechaCreacion());
        }
        return usuario;
    }

    private UsuarioDTO convertModelToDTO(UsuarioModel model) {
        String rolNombre = null;
        if (model.getRol() != null) {
            rolNombre = model.getRol().getNombre();
        }
        return new UsuarioDTO(
                model.getId_usuario(),
                model.getNombre(),
                model.getEmail(),
                model.getPassword(),
                model.getTelefono(),
                model.getDireccion(),
                model.getRolId(),
                rolNombre,
                model.getFechaRegistro());
    }

}
