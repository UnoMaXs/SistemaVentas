package com.sistemapos.sistemaventas.service.impl;

import com.sistemapos.sistemaventas.model.Producto;
import com.sistemapos.sistemaventas.repository.ProductosRepository;
import com.sistemapos.sistemaventas.service.IProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService {

    private final ProductosRepository productosRepository;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) productosRepository.findAll();
    }

    @Override
    public Optional<Producto> findById(Integer id) {
        return productosRepository.findById(id);
    }

    @Override
    public Producto findFirstByCodigo(String codigo) {
        return productosRepository.findFirstByCodigo(codigo);
    }

    @Override
    public Producto save(Producto producto) {
        return productosRepository.save(producto);
    }

    @Override
    public boolean deleteById(Integer id) {
        if (productosRepository.existsById(id)) {
            productosRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<Producto> actualizarProducto(Integer id, Producto producto) {
        return findById(id).map(existingProducto -> {
            if (!producto.getCodigo().equals(existingProducto.getCodigo())
                    && findFirstByCodigo(producto.getCodigo()) != null) {
                throw new IllegalArgumentException("Código duplicado");
            }
            producto.setId(id);
            return save(producto);
        });
    }

    public Producto crearProducto(Producto producto) {
        if (findFirstByCodigo(producto.getCodigo()) != null) {
            throw new IllegalArgumentException("Código duplicado");
        }
        return save(producto);
    }
}
