package com.sistemapos.sistemaventas.repository;

import com.sistemapos.sistemaventas.model.Venta;
import org.springframework.data.repository.CrudRepository;

public interface VentasRepository extends CrudRepository<Venta, Integer> {
}
