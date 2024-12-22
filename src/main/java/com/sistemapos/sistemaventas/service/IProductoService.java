package com.sistemapos.sistemaventas.service;

import com.sistemapos.sistemaventas.model.Producto;

import java.util.Optional;

public interface IProductoService {
    Iterable<Producto> findAll();
    Optional<Producto> findById(Integer id);
    Producto findFirstByCodigo(String codigo);
    Producto save(Producto producto);
    boolean deleteById(Integer id);
}
