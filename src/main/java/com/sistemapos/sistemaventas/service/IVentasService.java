package com.sistemapos.sistemaventas.service;

import com.sistemapos.sistemaventas.model.Venta;

import java.util.List;

public interface IVentasService {

    List<Venta> findAll();
}
