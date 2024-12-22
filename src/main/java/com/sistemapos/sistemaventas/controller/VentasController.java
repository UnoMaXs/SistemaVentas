package com.sistemapos.sistemaventas.controller;

import com.sistemapos.sistemaventas.model.Venta;
import com.sistemapos.sistemaventas.service.impl.VentasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/ventas")
@RequiredArgsConstructor
public class VentasController {

    private final VentasService ventasService;

    @GetMapping
    public ResponseEntity<List<Venta>> mostrarVentas() {
        List<Venta> ventas = ventasService.findAll();
        return ResponseEntity.ok(ventas);
    }
}

