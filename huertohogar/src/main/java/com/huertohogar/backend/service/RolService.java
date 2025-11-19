package com.huertohogar.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.huertohogar.backend.dto.RolDto;
import com.huertohogar.backend.model.RolModel;
import com.huertohogar.backend.repository.RolRepository;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public RolDto crearRol(RolDto rolDto) {

        if (rolRepository.existsByNombre(rolDto.getNombre())) {

            throw new RuntimeException("El rol '" + rolDto.getNombre() + "' ya existe");
        }

        RolModel rol = convertirDTOToModel(rolDto);

        RolModel rolGuardado = rolRepository.save(rol);

        return convertirModelToDTO(rolGuardado);
    }

    public List<RolDto> obtenerRoles() {

        return rolRepository.findAll()
                .stream()
                .map(this::convertirModelToDTO)
                .collect(Collectors.toList());
    }

    public RolDto obtenerPorId(Long id) {
        RolModel rol = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        return convertirModelToDTO(rol);
    }

    public RolDto obtenerPorNombre(String nombre) {
        RolModel rol = rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con nombre: " + nombre));

        return convertirModelToDTO(rol);
    }

    public RolDto actualizarRol(Long id, RolDto rolDto) {

        RolModel rolExistente = rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));

        rolExistente.setNombre(rolDto.getNombre());
        rolExistente.setDescripcion(rolDto.getDescripcion());

        RolModel rolactualizado = rolRepository.save(rolExistente);

        return convertirModelToDTO(rolactualizado);
    }

    public void eliminarRol(Long id) {

        if (!rolRepository.existsById(id)) {

            throw new RuntimeException("Rol no encontrado con id: " + id);
        }
        rolRepository.deleteById(id);
    }

    private RolModel convertirDTOToModel(RolDto rolDto) {

        return new RolModel(
                rolDto.getIdRol(),
                rolDto.getNombre(),
                rolDto.getDescripcion());

    }

    private RolDto convertirModelToDTO(RolModel rolModel) {

        return new RolDto(
                rolModel.getId(),
                rolModel.getNombre(),
                rolModel.getDescripcion());

    }

}
