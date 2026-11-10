
package com.ciclo.service;

import com.ciclo.domain.*;
import com.ciclo.repository.*;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaService {

    private final VentaRepository ventas;
    private final ClienteRepository clientes;
    private final BicicletaRepository bicicletas;
    private final ProductoAccesorioRepository accesorios;

    public VentaService(
        VentaRepository ventas,
        ClienteRepository clientes,
        BicicletaRepository bicicletas,
        ProductoAccesorioRepository accesorios
    ) {
        this.ventas = ventas;
        this.clientes = clientes;
        this.bicicletas = bicicletas;
        this.accesorios = accesorios;
    }

    @Transactional
    public Venta registrar(
        Long clienteId,
        String tipo,
        Long productoId,
        int cantidad
    ) {

        if (cantidad < 1) {
            throw new IllegalArgumentException(
                "La cantidad debe ser mayor que cero"
            );
        }

        Cliente cliente = clientes.findById(clienteId)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "El cliente no existe"
                )
            );

        Venta venta = new Venta();
        venta.setCliente(cliente);

        if ("BICICLETA".equals(tipo)) {

            if (cantidad != 1) {
                throw new IllegalArgumentException(
                    "Solo se permite una bicicleta " +
                    "por transacción"
                );
            }

            Bicicleta b = bicicletas
                .findByIdForUpdate(productoId)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "La bicicleta no existe"
                    )
                );

            if (!Boolean.TRUE.equals(
                    b.getDisponible())) {
                throw new IllegalArgumentException(
                    "La bicicleta ya fue vendida"
                );
            }

            venta.agregar(
                new DetalleVenta(
                    b.getMarca() + " " +
                    b.getModelo() + " (" +
                    b.getNumeroSerie() + ")",
                    1,
                    b.getPrecio()
                )
            );

            b.setDisponible(false);

        } else if ("ACCESORIO".equals(tipo)) {

            ProductoAccesorio a = accesorios
                .findByIdForUpdate(productoId)
                .orElseThrow(() ->
                    new IllegalArgumentException(
                        "El producto no existe"
                    )
                );

            if (a.getCantidad() < cantidad) {
                throw new IllegalArgumentException(
                    "No hay existencias suficientes"
                );
            }

            venta.agregar(
                new DetalleVenta(
                    a.getDescripcion(),
                    cantidad,
                    a.getPrecio()
                )
            );

            a.setCantidad(
                a.getCantidad() - cantidad
            );

        } else {
            throw new IllegalArgumentException(
                "Tipo de producto inválido"
            );
        }

        BigDecimal total = venta.getDetalles()
            .stream()
            .map(DetalleVenta::getSubtotal)
            .reduce(
                BigDecimal.ZERO,
                BigDecimal::add
            );

        venta.setTotal(total);

        return ventas.save(venta);
    }

    @Transactional(readOnly = true)
    public Venta comprobante(Long id) {
        return ventas.findWithDetallesById(id)
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Venta no encontrada"
                )
            );
    }
}
