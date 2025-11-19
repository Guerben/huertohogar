package com.huertohogar.backend.controller;

import com.huertohogar.backend.dto.RolDto;
import com.huertohogar.backend.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;



@RestController
@RequestMapping("/api/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolService rolService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearRol(@RequestBody RolDto rolDto) {
        try {

            RolDto rol = rolService.crearRol(rolDto);
            return new ResponseEntity<>(rol, HttpStatus.CREATED);
            
        } catch (RuntimeException e) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/obtener")
    public ResponseEntity<List<RolDto>> obtenerRoles(){
        List<RolDto> roles = rolService.obtenerRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        try {
            RolDto rol = rolService.obtenerPorId(id);
            return new ResponseEntity<>(rol, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    

    @GetMapping("obtener/nombre/{nombre}")
    public ResponseEntity<?> obtenerPorNombre(@PathVariable String nombre){
        try {
            RolDto rol = rolService.obtenerPorNombre(nombre);
            return new ResponseEntity<>(rol, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }


    @PutMapping("actualizar/{id}")
    public ResponseEntity<?> actualizarRol(@PathVariable Long id, @RequestBody RolDto rolDto){
        try {
            RolDto rol = rolService.actualizarRol(id, rolDto);
            return new ResponseEntity<>(rol, HttpStatus.OK);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
    
    @DeleteMapping("eliminar/{id}")
    public ResponseEntity<?> eliminarRol(@PathVariable Long id) {
        try {
            rolService.eliminarRol(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    
    


}
