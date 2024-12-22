package com.sistemapos.sistemaventas.service.impl;

import com.sistemapos.sistemaventas.model.Producto;
import com.sistemapos.sistemaventas.model.ProductoParaVender;
import com.sistemapos.sistemaventas.model.ProductoVendido;
import com.sistemapos.sistemaventas.model.Venta;
import com.sistemapos.sistemaventas.repository.ProductosVendidosRepository;
import com.sistemapos.sistemaventas.repository.VentasRepository;
import com.sistemapos.sistemaventas.service.IProductoService;
import com.sistemapos.sistemaventas.service.IVenderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class VenderService implements IVenderService {

    private final VentasRepository ventasRepository;
    private final ProductosVendidosRepository productosVendidosRepository;
    private final IProductoService productoService;

    @Override
    public Venta save(Venta venta) {
        return ventasRepository.save(venta);
    }

    public ArrayList<ProductoParaVender> obtenerCarrito(HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = (ArrayList<ProductoParaVender>) request.getSession().getAttribute("carrito");
        if (carrito == null) {
            carrito = new ArrayList<>();
        }
        return carrito;
    }

    public void guardarCarrito(ArrayList<ProductoParaVender> carrito, HttpServletRequest request) {
        request.getSession().setAttribute("carrito", carrito);
    }

    public void limpiarCarrito(HttpServletRequest request) {
        guardarCarrito(new ArrayList<>(), request);
    }

    public void quitarDelCarrito(int indice, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = obtenerCarrito(request);
        if (carrito != null && indice >= 0 && indice < carrito.size()) {
            carrito.remove(indice);
            guardarCarrito(carrito, request);
        }
    }

    public Map<String, Object> interfazVender(HttpServletRequest request) {
        Map<String, Object> response = new HashMap<>();
        Producto producto = new Producto();
        response.put("producto", producto);

        ArrayList<ProductoParaVender> carrito = obtenerCarrito(request);
        float total = carrito.stream().map(ProductoParaVender::getTotal).reduce(0f, Float::sum);
        response.put("total", total);
        response.put("carrito", carrito);

        return response;
    }

    public String agregarAlCarrito(Producto producto, HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = obtenerCarrito(request);

        Producto productoBuscadoPorCodigo = productoService.findFirstByCodigo(producto.getCodigo());
        if (productoBuscadoPorCodigo == null) {
            return "El producto con el código " + producto.getCodigo() + " no existe.";
        }

        if (productoBuscadoPorCodigo.sinExistencia()) {
            return "El producto está agotado.";
        }

        boolean encontrado = false;
        for (ProductoParaVender productoParaVenderActual : carrito) {
            if (productoParaVenderActual.getCodigo().equals(productoBuscadoPorCodigo.getCodigo())) {
                productoParaVenderActual.aumentarCantidad();
                encontrado = true;
                break;
            }
        }

        if (!encontrado) {
            carrito.add(new ProductoParaVender(
                    productoBuscadoPorCodigo.getNombre(),
                    productoBuscadoPorCodigo.getCodigo(),
                    productoBuscadoPorCodigo.getPrecio(),
                    productoBuscadoPorCodigo.getExistencia(),
                    productoBuscadoPorCodigo.getId(),
                    1f
            ));
        }

        guardarCarrito(carrito, request);
        return "Producto agregado al carrito exitosamente.";
    }

    public String terminarVenta(HttpServletRequest request) {
        ArrayList<ProductoParaVender> carrito = obtenerCarrito(request);

        if (carrito == null || carrito.isEmpty()) {
            return "El carrito está vacío. No se puede procesar la venta.";
        }

        Venta venta = save(new Venta());

        carrito.forEach(productoParaVender -> {
            Producto producto = productoService.findById(productoParaVender.getId()).orElse(null);
            if (producto != null) {
                producto.restarExistencia(productoParaVender.getCantidad());
                productoService.save(producto);

                ProductoVendido productoVendido = new ProductoVendido(
                        productoParaVender.getCantidad(),
                        productoParaVender.getPrecio(),
                        productoParaVender.getNombre(),
                        productoParaVender.getCodigo(),
                        venta
                );
                productosVendidosRepository.save(productoVendido);
            }
        });

        limpiarCarrito(request);
        return "Venta realizada correctamente.";
    }
}
