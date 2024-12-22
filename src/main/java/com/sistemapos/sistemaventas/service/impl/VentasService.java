package com.sistemapos.sistemaventas.service.impl;

import com.sistemapos.sistemaventas.model.Venta;
import com.sistemapos.sistemaventas.repository.VentasRepository;
import com.sistemapos.sistemaventas.service.IVentasService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VentasService implements IVentasService {

    private final VentasRepository ventasRepository;

    @Override
    public List<Venta> findAll() {
        return (List<Venta>) ventasRepository.findAll();
    }
}
