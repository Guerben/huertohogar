package com.huertohogar.backend.service;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.huertohogar.backend.dto.ProductoDto;
import com.huertohogar.backend.model.Producto;
import com.huertohogar.backend.repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setIdProducto(1L);
        producto.setCodigo("P001");
        producto.setNombre("Tomate");
        producto.setDescripcion("Tomate cherry");
        producto.setPrecio(1.5);
        producto.setStock(100);
        producto.setOrigen("Local");
        producto.setImagenUrl("http://example.com/tomate.jpg");
    }

    @Test
    void listarProductos_deberiaDevolverListaDto() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<ProductoDto> lista = productoService.listarProductos();

        assertNotNull(lista);
        assertEquals(1, lista.size());
        assertEquals(producto.getNombre(), lista.get(0).getNombre());
    }

    @Test
    void guardarProducto_deberiaGuardarYDevolverDto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        ProductoDto dto = new ProductoDto(null, producto.getCodigo(), producto.getNombre(), producto.getDescripcion(),
                producto.getPrecio(), producto.getStock(), producto.getOrigen(), producto.getImagenUrl());

        ProductoDto guardado = productoService.guardarProducto(dto);

        assertNotNull(guardado);
        assertEquals(producto.getCodigo(), guardado.getCodigo());

        ArgumentCaptor<Producto> captor = ArgumentCaptor.forClass(Producto.class);
        verify(productoRepository).save(captor.capture());
        Producto savedEntity = captor.getValue();
        assertEquals(dto.getNombre(), savedEntity.getNombre());
    }

    @Test
    void obtenerPorId_siExiste_deberiaDevolverDto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        ProductoDto dto = productoService.obtenerPorId(1L);

        assertNotNull(dto);
        assertEquals(producto.getIdProducto(), dto.getId());
    }

    @Test
    void eliminar_deberiaInvocarDeleteById() {
        doNothing().when(productoRepository).deleteById(1L);

        productoService.eliminar(1L);

        verify(productoRepository).deleteById(1L);
    }
}
