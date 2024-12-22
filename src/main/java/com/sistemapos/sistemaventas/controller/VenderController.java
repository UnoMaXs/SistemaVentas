package com.sistemapos.sistemaventas.controller;

import com.sistemapos.sistemaventas.model.Producto;
import com.sistemapos.sistemaventas.service.impl.VenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/vender")
public class VenderController {

    private final VenderService venderService;

    @DeleteMapping(value = "/quitar/{indice}")
    public ResponseEntity<String> quitarDelCarrito(@PathVariable int indice, HttpServletRequest request) {
        venderService.quitarDelCarrito(indice, request);
        return ResponseEntity.ok("Producto quitado del carrito exitosamente.");
    }

    @DeleteMapping(value = "/limpiar")
    public ResponseEntity<String> cancelarVenta(HttpServletRequest request) {
        venderService.limpiarCarrito(request);
        return ResponseEntity.ok("Venta cancelada. El carrito ha sido limpiado.");
    }

    @PostMapping(value = "/terminar")
    public ResponseEntity<String> terminarVenta(HttpServletRequest request) {
        String mensaje = venderService.terminarVenta(request);
        return ResponseEntity.ok(mensaje);
    }

    @GetMapping(value = "/")
    public ResponseEntity<Map<String, Object>> interfazVender(HttpServletRequest request) {
        Map<String, Object> response = venderService.interfazVender(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/agregar")
    public ResponseEntity<String> agregarAlCarrito(@RequestBody Producto producto, HttpServletRequest request) {
        String mensaje = venderService.agregarAlCarrito(producto, request);
        return ResponseEntity.ok(mensaje);
    }
}

