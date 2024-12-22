package com.sistemapos.sistemaventas.repository;

import com.sistemapos.sistemaventas.model.Producto;
import org.springframework.data.repository.CrudRepository;

public interface ProductosRepository extends CrudRepository<Producto, Integer> {

    Producto findFirstByCodigo(String codigo);
}
